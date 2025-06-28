import { useEffect, useState } from "react";
import type { TStats } from "../lib/types/historyTypes";
import type { TSnakeCustomization } from "../lib/types/customizationTypes";
import { getStats } from "../lib/api/historyService";
import { getSnake } from "../lib/api/customizationService";
import SnakePreview from "../components/SnakePreview";
import { useAuth } from "../context/AuthContext";
import Stats from "../components/dashboard/Stats";
import Unauthorized from "../components/Unauthorized";
import Landing from "../components/dashboard/Landing";

export default function Dashboard() {
	const [stats, setStats] = useState<TStats | null>(null);
	const [snake, setSnake] = useState<TSnakeCustomization | null>(null);

	const { user, authenticated } = useAuth();

	useEffect(() => {
		if (authenticated && user) {
			fetchGameHistory();
			fetchSnake();
		}
	}, [authenticated, user]);

	function fetchGameHistory(): void {
		getStats()
			.then((data) => {
				if (data) {
					setStats(data);
				} else {
					console.error("Invalid stats format:", data);
				}
			})
			.catch((error) => {
				console.error("Failed to fetch game stats:", error);
			});
	}

	function fetchSnake(): void {
		getSnake()
			.then((data) => {
				if (data) {
					setSnake(data);
				} else {
					console.error("Invalid snake customization format:", data);
				}
			})
			.catch((error) => {
				console.error("Failed to fetch snake customization:", error);
			});
	}

	return (
		<div className="p-6 max-w-5xl mx-auto space-y-8">
			{authenticated && user && stats && snake ? (
				<Stats stats={stats} snake={snake} />
			) : (
				<Landing />
			)}
		</div>
	);
}
