import { type JSX } from "react";
import { useNavigate } from "react-router-dom";
import { MdDelete } from "react-icons/md";

import type { TGameHistory } from "../../lib/types/historyTypes";

type HistoryListProps = {
	gameHistory: TGameHistory[];
	onDelete: (id: string) => void;
};

function HistoryList({ gameHistory, onDelete }: HistoryListProps): JSX.Element {
	const navigate = useNavigate();

	return (
		<ul className="space-y-2">
			{gameHistory.map((game) => (
				<li
					key={game.gameId}
					onClick={() => navigate(`/history/${game.gameId}`)}
					className="group flex items-center justify-between p-4 bg-white rounded-lg shadow-sm border border-gray-200 cursor-pointer hover:bg-blue-100 hover:shadow-md transition-all duration-200"
				>
					<p className="font-semibold text-sm text-gray-800">
						Game ID:{" "}
						<span className="text-blue-600">{game.gameId}</span>
					</p>
					<button
						onClick={(e) => {
							e.stopPropagation();
							onDelete(game.gameId);
						}}
						className="ml-4 p-1 hover:brightness-75 hover:cursor-pointer text-white bg-red-500 rounded opacity-0 group-hover:opacity-100 transition-all duration-200"
						aria-label="Delete game"
					>
						<MdDelete className="w-5 h-5" />
					</button>
				</li>
			))}
		</ul>
	);
}

export default HistoryList;
