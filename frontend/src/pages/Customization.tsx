import React, { useEffect, useState } from "react";
import { getSnake, updateSnake } from "../lib/api/customizationService";
import SnakePreview from "../components/SnakePreview";

const heads = ["default", "beluga", "evil", "sand-worm", "silly"];
const tails = ["default", "round-bum", "small-rattle", "freckled", "curled"];

export default function Customization() {
	const [snake, setSnake] = useState({
		author: "",
		color: "#000000",
		head: "default",
		tail: "default",
	});
	const [loading, setLoading] = useState(true);
	const [updating, setUpdating] = useState(false);
	const [hasChanges, setHasChanges] = useState(false);

	useEffect(() => {
		getSnake().then((data) => {
			setSnake({
				author: data.author || "",
				color: data.color || "#000000",
				head: data.head || "default",
				tail: data.tail || "default",
			});
			setLoading(false);
			setHasChanges(false);
		});
	}, []);

	function handleChange(
		e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>,
	) {
		setSnake((prev) => ({
			...prev,
			[e.target.name]: e.target.value,
		}));
		setHasChanges(true);
	}

	async function handleUpdate(e: React.FormEvent) {
		e.preventDefault();
		setUpdating(true);
		try {
			await updateSnake(snake);
			setHasChanges(false);
		} catch (error) {
			console.error("Failed to update snake", error);
		}
		setUpdating(false);
	}

	if (loading) {
		return <p className="text-center mt-10 text-gray-500">Loading...</p>;
	}

	return (
		<div className="flex overflow-y-auto gap-10 flex-row items-center justify-center w-full">
			{/* Left side: form */}
			<form
				onSubmit={handleUpdate}
				className="flex-1 space-y-5 max-w-md bg-white h-min rounded-lg shadow-md p-6"
				aria-label="Snake customization form"
			>
				<h1 className="text-3xl font-bold mb-6 text-center">
					Mini Snek Metadata
				</h1>

				<div>
					<label
						className="block font-semibold mb-1"
						htmlFor="author"
					>
						Author
					</label>
					<input
						id="author"
						name="author"
						type="text"
						value={snake.author}
						disabled
						className="w-full px-3 py-2 border border-gray-300 rounded bg-gray-100 cursor-not-allowed"
					/>
				</div>

				<div>
					<label className="block font-semibold mb-1" htmlFor="color">
						Color
					</label>
					<input
						id="color"
						name="color"
						type="color"
						value={snake.color}
						onChange={handleChange}
						className="w-full h-10 p-0 border border-gray-300 rounded cursor-pointer"
					/>
				</div>

				<div>
					<label className="block font-semibold mb-1" htmlFor="head">
						Head
					</label>
					<select
						id="head"
						name="head"
						value={snake.head}
						onChange={handleChange}
						className="w-full px-3 py-2 border border-gray-300 rounded"
					>
						{heads.map((headOption) => (
							<option key={headOption} value={headOption}>
								{headOption}
							</option>
						))}
					</select>
				</div>

				<div>
					<label className="block font-semibold mb-1" htmlFor="tail">
						Tail
					</label>
					<select
						id="tail"
						name="tail"
						value={snake.tail}
						onChange={handleChange}
						className="w-full px-3 py-2 border border-gray-300 rounded"
					>
						{tails.map((tailOption) => (
							<option key={tailOption} value={tailOption}>
								{tailOption}
							</option>
						))}
					</select>
				</div>

				<button
					type="submit"
					disabled={!hasChanges || updating}
					className={`w-full py-2 rounded text-white font-semibold transition-colors duration-200 ${
						hasChanges && !updating
							? "bg-blue-600 hover:bg-blue-700 cursor-pointer"
							: "bg-gray-400 cursor-not-allowed"
					}`}
				>
					{updating ? "Updating..." : "Update"}
				</button>
			</form>
			<div className="max-w-md">
				<SnakePreview
					head={snake.head}
					tail={snake.tail}
					color={snake.color}
				/>
			</div>{" "}
		</div>
	);
}
