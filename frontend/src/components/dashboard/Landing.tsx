export default function Landing() {
	return (
		<div className="min-h-full flex items-center justify-center bg-gradient-to-br from-slate-50 to-slate-200 p-6">
			<div className="max-w-2xl w-full text-center bg-white rounded-xl shadow-lg p-10 border border-slate-100">
				<h1 className="text-4xl sm:text-5xl font-extrabold text-slate-800 mb-6">
					🐍 Welcome to Battlesnake Trainer
				</h1>
				<p className="text-lg text-slate-700 mb-4">
					Create, customize, and track your Battlesnake. Compete with
					friends and improve your snake’s performance!
				</p>
				<p className="text-md text-slate-600 mb-6">
					Get started by logging in. For demo purposes, use one of the
					following accounts:
				</p>

				<div className="grid sm:grid-cols-2 gap-6 text-left text-sm text-slate-800">
					<div className="bg-slate-50 border rounded-lg p-4">
						<h3 className="font-semibold text-slate-700 mb-1">
							User
						</h3>
						<code className="block bg-white p-2 rounded border text-sm">
							username: user
							<br />
							password: user
						</code>
					</div>
					<div className="bg-slate-50 border rounded-lg p-4">
						<h3 className="font-semibold text-slate-700 mb-1">
							Admin
						</h3>
						<code className="block bg-white p-2 rounded border text-sm">
							username: admin
							<br />
							password: admin
						</code>
					</div>
				</div>
			</div>
		</div>
	);
}
