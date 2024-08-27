/* eslint-disable react/prop-types */
import { useState, useEffect } from "react"
import { getCategories } from "../utils/fecthApi"

export default function RoomCategorySelector({ handleRoomInputChange, newRoom }){

    const [categories, setCategories] = useState([""])
	const [showCategoryNew, setShowCategoryNew] = useState(false)
	const [categoryNew, setCategoryNew] = useState("")

	useEffect(() => {
		getCategories().then((data) => {
            console.log(data)
			setCategories(data)
		})
	}, [])

	const handleCategoryNewInputChange = (e) => {
		setCategoryNew(e.target.value)
	}

	const handleAddCategoryNew = () => {
		if (categoryNew !== "") {
			setCategories([...categories, categoryNew])
			setCategoryNew("")
			setShowCategoryNew(false)
		}
	}

	return (
		<>
			{categories.length > 0 && (
				<div>
					<select
						required
						className="form-select"
						name="categoryId"
						onChange={(e) => {
							if (e.target.value === "Add New") {
								setShowCategoryNew(true)
							} else {
								handleRoomInputChange(e)
							}
						}}
						value={newRoom.categoryId}>
						<option value="">Select a room category</option>
						<option value={"Add New"}>Add New</option>
						{categories.map((type, index) => (
							<option key={index} value={type}>
								{type}
							</option>
						))}
					</select>
					{showCategoryNew && (
						<div className="mt-2">
							<div className="input-group">
								<input
									type="text"
									className="form-control"
									placeholder="Enter New Room Category"
									value={categoryNew}
									onChange={handleCategoryNewInputChange}
								/>
								<button className="btn btn-hotel" type="button" onClick={handleAddCategoryNew}>
									Add
								</button>
							</div>
						</div>
					)}
				</div>
			)}
		</>
	)
}