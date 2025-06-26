import { useEffect, useState, type JSX } from "react";
import { Outlet } from "react-router-dom";
import {
	getGameHistory,
	deleteGameFromHistory,
} from "../lib/api/historyService";

import type { TGameHistory } from "../lib/types/historyTypes";

import HistoryList from "../components/history/HistoryList";

function History(): JSX.Element {
	const [gameHistory, setGameHistory] = useState<TGameHistory[]>([]);

	useEffect(() => {
		fetchGameHistory();
	}, []);

	function fetchGameHistory(): void {
		getGameHistory()
			.then((history) => {
				if (Array.isArray(history)) {
					setGameHistory(history);
				} else {
					console.error("Invalid game history format:", history);
				}
			})
			.catch((error) => {
				console.error("Failed to fetch game history:", error);
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

	return (
		<>
			<div className="w-1/3 bg-gray-100 p-4 overflow-y-auto border-r border-gray-300">
				<h2 className="text-lg font-bold mb-4">Game History</h2>
				<HistoryList
					gameHistory={gameHistory}
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
