import { useEffect, useState, type JSX } from "react";
import { Outlet, useNavigate } from "react-router-dom";
import {
	getGameHistory,
	deleteGameFromHistory,
} from "../lib/api/historyService";

import type { TGameHistory } from "../lib/types/historyTypes";

import HistoryList from "../components/history/HistoryList";
import { useAuth } from "../hooks/useAuth";
import Loading from "../components/Loading";

function History(): JSX.Element {
	const [gameHistory, setGameHistory] = useState<TGameHistory[]>([]);
	const [loading, setLoading] = useState(true);

	const { user, authenticated, roles } = useAuth();

	const navigate = useNavigate();

	useEffect(() => {
		if (
			!authenticated ||
			!user ||
			(roles && !roles.includes("user") && !roles.includes("admin"))
		) {
			navigate("/");
			return;
		}

		fetchGameHistory();
	}, [authenticated, user, roles, navigate]);

	function fetchGameHistory(): void {
		setLoading(true);
		getGameHistory()
			.then((history) => {
				if (Array.isArray(history)) {
					setGameHistory(history);
				} else {
					console.error("Invalid game history format:", history);
				}
				setLoading(false);
			})
			.catch((error) => {
				console.error("Failed to fetch game history:", error);
				setLoading(false);
			});
	}

	function handleDelete(id: string): void {
		if (!id) return;

		deleteGameFromHistory(id)
			.then(() => {
				fetchGameHistory();
			})
			.catch((error) => {
				console.error(`Failed to delete game ${id}:`, error);
			});
	}

	if (loading) {
		return <Loading text="Loading game history..." />;
	}

	return (
		<>
			<div className="w-1/3 bg-gray-100 p-4 overflow-y-auto border-r border-gray-300">
				<h2 className="text-lg font-bold mb-4">Game History</h2>
				<HistoryList
					gameHistory={gameHistory}
					roles={roles}
					onDelete={handleDelete}
				/>
			</div>
			<div className="w-2/3 p-6 overflow-y-auto">
				<Outlet />
			</div>
		</>
	);
}

export default History;
