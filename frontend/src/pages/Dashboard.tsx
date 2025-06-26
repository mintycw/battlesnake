export default function Dashboard() {
	// Dummy data for now — replace with real API data later
	const stats = {
		gamesPlayed: 42,
		winRate: 58, // percent
		averageLength: 15,
	};

	const snakePreview = {
		color: "#4ade80", // green
		head: "belly",
		tail: "curled",
		author: "admin",
		version: "2.1",
	};

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

			{/* Snake preview */}
			<section className="bg-white rounded-lg shadow p-6 flex items-center space-x-6">
				<div
					className="w-24 h-24 rounded-full"
					style={{ backgroundColor: snakePreview.color }}
					title="Your snake's color"
				/>
				<div>
					<h2 className="text-xl font-semibold text-slate-800 mb-2">
						Your Current Snake
					</h2>
					<ul className="text-gray-700 space-y-1">
						<li>
							<strong>Author:</strong> {snakePreview.author}
						</li>
						<li>
							<strong>Head:</strong> {snakePreview.head}
						</li>
						<li>
							<strong>Tail:</strong> {snakePreview.tail}
						</li>
						<li>
							<strong>Version:</strong> {snakePreview.version}
						</li>
					</ul>
				</div>
			</section>
		</div>
	);
}
