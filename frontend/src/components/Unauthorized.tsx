export default function Unauthorized() {
	return (
		<div className="min-h-full flex items-center justify-center px-4">
			<div className="max-w-xl w-full bg-slate-100 shadow-md rounded-lg p-8">
				<h1 className="text-3xl font-bold text-red-600 mb-4">
					Access Denied
				</h1>
				<p className="text-gray-700 mb-6">
					You don't have permission to view this page. It looks like
					you tried to enter a restricted area.
				</p>
				<div className="prose prose-sm max-w-none text-gray-600">
					<p>
						If you believe this is a mistake, make sure you’re
						logged in with the correct account or contact an
						administrator. Sometimes access issues can be resolved
						by refreshing your session or logging in again.
					</p>
				</div>
			</div>
		</div>
	);
}
