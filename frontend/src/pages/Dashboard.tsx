import { useEffect, useState } from "react";
import type { TStats } from "../lib/types/historyTypes";
import type { TSnakeCustomization } from "../lib/types/customizationTypes";
import { getStats } from "../lib/api/historyService";
import { getSnake } from "../lib/api/customizationService";
import SnakePreview from "../components/SnakePreview";

export default function Dashboard() {
	const [stats, setStats] = useState<TStats | null>(null);
	const [snake, setSnake] = useState<TSnakeCustomization | null>(null);

	useEffect(() => {
		fetchGameHistory();
		fetchSnake();
	}, []);

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
			<header>
				<h1 className="text-3xl font-bold text-slate-800 mb-1">
					Welcome back, Battlesnake Trainer!
				</h1>
				<p className="text-slate-600">
					Customize your snake and track your progress here.
				</p>
			</header>

			{/* Stats cards */}
			{stats && (
				<section className="grid grid-cols-1 sm:grid-cols-3 gap-6">
					<div className="bg-white rounded-lg shadow p-5 text-center">
						<p className="text-4xl font-extrabold text-blue-600">
							{stats.gamesPlayed}
						</p>
						<p className="text-gray-600 mt-1">Games Played</p>
					</div>
					<div className="bg-white rounded-lg shadow p-5 text-center">
						<p className="text-4xl font-extrabold text-green-600">
							{stats.winRate}%
						</p>
						<p className="text-gray-600 mt-1">Win Rate</p>
					</div>
					<div className="bg-white rounded-lg shadow p-5 text-center">
						<p className="text-4xl font-extrabold text-yellow-600">
							{stats.averageLength}
						</p>
						<p className="text-gray-600 mt-1">Average Length</p>
					</div>
				</section>
			)}

			{/* Snake preview */}
			{snake && (
				<>
					<section className="bg-white rounded-lg shadow p-6 flex items-center space-x-6">
						<div
							className="w-24 h-24 rounded-full"
							style={{ backgroundColor: snake.color }}
							title="Your snake's color"
						/>
						<div>
							<h2 className="text-xl font-semibold text-slate-800 mb-2">
								Your Current Snake
							</h2>
							<ul className="text-gray-700 space-y-1">
								<li>
									<strong>Author:</strong> {snake.author}
								</li>
								<li>
									<strong>Head:</strong> {snake.head}
								</li>
								<li>
									<strong>Tail:</strong> {snake.tail}
								</li>
							</ul>
						</div>
					</section>

					<SnakePreview
						head={snake.head}
						tail={snake.tail}
						color={snake.color}
					/>
				</>
			)}
		</div>
	);
}
