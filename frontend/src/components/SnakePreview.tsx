type SnakePreviewProps = {
	head: string;
	tail: string;
	color: string;
};

export default function SnakePreview({ head, tail, color }: SnakePreviewProps) {
	const avatarUrl = `https://exporter.battlesnake.com/avatars/head:${head}/tail:${tail}/color:${encodeURIComponent(
		color,
	)}/200x30.svg`;

	return (
		<div className="w-full bg-white h-min rounded-lg shadow-md p-6">
			<h2 className="text-xl font-semibold mb-4">Preview</h2>
			<img
				src={avatarUrl}
				alt={`Battlesnake preview: head ${head}, tail ${tail}, color ${color}`}
				className="rounded-lg w-auto h-20"
			/>
		</div>
	);
}
