import React, { useEffect, useState } from "react";
import { getSnake, updateSnake } from "../lib/api/customizationService";
import SnakePreview from "../components/SnakePreview";
import { useAuth } from "../hooks/useAuth";
import { useNavigate } from "react-router-dom";
import Loading from "../components/Loading";
import { heads, tails } from "../data/snakeCustomizationOptions.json";

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

	const { user, authenticated, roles } = useAuth();
	const navigate = useNavigate();

	useEffect(() => {
		if (
			!authenticated ||
			!user ||
			(roles && !roles.includes("user") && !roles.includes("admin"))
		) {
			navigate("/");
			return;
		}

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
	}, [authenticated, user, roles, navigate]);

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

	function isDisabled(): boolean {
		if (!roles) return true;

		if (roles.includes("admin")) return false;

		return true;
	}

	if (loading) {
		return <Loading text="Loading snake customization..." />;
	}

	return (
		<div className="flex overflow-y-auto gap-10 flex-col lg:flex-row items-center justify-center w-full">
			{/* Left side: form */}
			<form
				onSubmit={handleUpdate}
				className="flex-1 space-y-5 w-full lg:max-w-md bg-white h-min lg:rounded-lg shadow-md p-6"
				aria-label="Snake customization form"
			>
				<h1 className="text-3xl font-bold mb-6 text-center">
					Snake Customization
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
						disabled={isDisabled()}
						className={`w-full h-10 p-0 border border-gray-300 rounded ${
							isDisabled()
								? "bg-gray-100 cursor-not-allowed"
								: "cursor-pointer"
						}`}
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
						disabled={isDisabled()}
						className={`w-full h-10 p-0 border border-gray-300 rounded ${
							isDisabled()
								? "bg-gray-100 cursor-not-allowed"
								: "cursor-pointer"
						}`}
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
						disabled={isDisabled()}
						className={`w-full h-10 p-0 border border-gray-300 rounded ${
							isDisabled()
								? "bg-gray-100 cursor-not-allowed"
								: "cursor-pointer"
						}`}
					>
						{tails.map((tailOption) => (
							<option key={tailOption} value={tailOption}>
								{tailOption}
							</option>
						))}
					</select>
				</div>

				{roles?.includes("admin") && (
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
				)}
			</form>
			<div className="w-full lg:max-w-md">
				<SnakePreview
					head={snake.head}
					tail={snake.tail}
					color={snake.color}
				/>
			</div>{" "}
		</div>
	);
}
