export type TGameHistory = {
	gameId: string;
};

export type TGameDetails = {
	gameId: string;
	totalTurns: number;
	startingSnakes: number;
	endingSnakes: number;
	yourSnakeName: string;
	startLength: number;
	endLength: number;
	startHealth: number;
	endHealth: number;
	survived: boolean;
};

export type TStats = {
	gamesPlayed: number;
	winRate: number; // percent
	averageLength: number; // average snake length
};
