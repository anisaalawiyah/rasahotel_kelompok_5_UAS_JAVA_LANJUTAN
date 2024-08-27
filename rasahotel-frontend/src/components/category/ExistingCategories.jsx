import { useEffect, useState } from "react"
import { deleteCategory, getCategories } from "../utils/fecthApi"
import { Col, Row } from "react-bootstrap"
import { FaEdit, FaPlus, FaTrashAlt } from "react-icons/fa"
import { Link } from "react-router-dom"

export default function ExistingCategories() {

    const [categories, setCategories] = useState([{ id: "", name: "", desc: "", saleId: {id: "",name: "", price: 0} }])
	const [isLoading, setIsLoading] = useState(false)
	const [errorMessage, setErrorMessage] = useState("")
	const [successMessage, setSuccessMessage] = useState("")

	useEffect(() => {
		fetchCategories()
	}, [])

	const fetchCategories = async () => {
		setIsLoading(true)
		try {
			const result = await getCategories()
			setCategories(result)
			setIsLoading(false)
		} catch (error) {
			setErrorMessage(error.message)
			setIsLoading(false)
		}
	}

	const handleDelete = async (categoryId) => {
		try {
			const result = await deleteCategory(categoryId)
			if (result.success) {
				setSuccessMessage(`Category was delete`)
				fetchCategories()
			} else {
				console.error(`Failed deleting category : ${result.responseMessage}`)
			}
		} catch (error) {
			setErrorMessage(error.message)
		}
		setTimeout(() => {
			setSuccessMessage("")
			setErrorMessage("")
		}, 3000)
	}

	return (
		<>
			<div className="container col-md-8 col-lg-6">
				{successMessage && <p className="alert alert-success mt-5">{successMessage}</p>}

				{errorMessage && <p className="alert alert-danger mt-5">{errorMessage}</p>}
			</div>

			{isLoading ? (
				<div className="alert alert-info" role="alert">Loading existing categories</div>
			) : (
				<>
					<section className="mt-5 mb-5 container">
						<div className="d-flex justify-content-between mb-3 mt-5">
							<h2>Existing Categories</h2>
						</div>

						<Row>

							<Col md={6} className="d-flex justify-content-end">
								<Link to={"/add-category"}>
									<FaPlus /> Add Category
								</Link>
							</Col>
						</Row>

						<table className="table table-bordered table-hover">
							<thead>
								<tr className="text-center">
									<th>No</th>
									<th>Category Name</th>
									<th>Category Sale</th>
									<th>Actions</th>
								</tr>
							</thead>

							<tbody>
								{categories.map((category,i) => (
									<tr key={i} className="text-center">
										<td>{i+1}</td>
										<td>{category.name}</td>
										<td>{category.saleId != null ? category.saleId.name : "No Sale Available"}</td>
										<td className="gap-2">
											<Link to={`/edit-category/${category.id}`}>
												<span className="btn btn-warning btn-sm ml-5">
													<FaEdit />
												</span>
											</Link>{" "}
											<button
												className="btn btn-danger btn-sm ml-5"
												onClick={() => handleDelete(category.id)}>
												<FaTrashAlt />
											</button>
										</td>
									</tr>
								))}
							</tbody>
						</table>
					</section>
				</>
			)}
		</>
	)
}