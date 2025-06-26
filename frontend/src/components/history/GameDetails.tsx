import { useEffect, useState, type JSX } from "react";
import { useParams } from "react-router-dom";
import { getGameDetails } from "../../lib/api/historyService";

import type { TGameDetails } from "../../lib/types/historyTypes";

function GameDetails(): JSX.Element {
	const { id } = useParams<{ id: string }>();
	const [details, setDetails] = useState<TGameDetails | null>(null);

	useEffect(() => {
		if (!id) return;

		getGameDetails(id)
			.then((data) => setDetails(data))
			.catch((err) => {
				console.error(`Failed to fetch details for game ${id}:`, err);
				setDetails(null);
			});
	}, [id]);

	if (!details) {
		return (
			<p className="text-gray-500 italic">
				Select a game to view details
			</p>
		);
	}

	return (
		<div
			key={details.gameId}
			className="bg-white rounded-xl shadow-lg p-6 hover:shadow-xl duration-300 animate-fade-in-scale border border-gray-200"
		>
			<h2 className="text-2xl font-bold mb-4 text-slate-700">
				Game Details
			</h2>
			<ul className="space-y-2 text-sm text-gray-800">
				<li>
					<strong>Game ID:</strong> {details.gameId}
				</li>
				<li>
					<strong>Total Turns:</strong> {details.totalTurns}
				</li>
				<li>
					<strong>Starting Snakes:</strong> {details.startingSnakes}
				</li>
				<li>
					<strong>Ending Snakes:</strong> {details.endingSnakes}
				</li>
				<li>
					<strong>Your Snake Name:</strong> {details.yourSnakeName}
				</li>
				<li>
					<strong>Start Length:</strong> {details.startLength}
				</li>
				<li>
					<strong>End Length:</strong> {details.endLength}
				</li>
				<li>
					<strong>Start Health:</strong> {details.startHealth}
				</li>
				<li>
					<strong>End Health:</strong> {details.endHealth}
				</li>
				<li>
					<strong>Survived:</strong>{" "}
					<span
						className={`font-bold ${
							details.survived ? "text-green-600" : "text-red-500"
						}`}
					>
						{details.survived ? "Yes" : "No"}
					</span>
				</li>
			</ul>
		</div>
	);
}

export default GameDetails;
