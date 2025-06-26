export type TSnakeCustomization = {
	color: string;
	head: string;
	tail: string;
	author: string;
	lastUpdated: number;
	updatedBy: string;
};

export type TSnakeCustomizationUpdate = {
	color?: string;
	head?: string;
	tail?: string;
};
