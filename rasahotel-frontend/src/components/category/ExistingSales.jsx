import { useEffect, useState } from "react"
import { deleteSale, getSales } from "../utils/fecthApi"
import { Col, Row } from "react-bootstrap"
import { FaEdit, FaPlus, FaTrashAlt } from "react-icons/fa"
import { Link } from "react-router-dom"

export default function ExistingSales() {

    const [sales, setSales] = useState([{ id: "", name: "", price: "" }])
	const [isLoading, setIsLoading] = useState(false)
	const [errorMessage, setErrorMessage] = useState("")
	const [successMessage, setSuccessMessage] = useState("")

	const fetchSales = async () => {
		setIsLoading(true)
		try {
			const result = await getSales()
			setSales(result.data)
			setIsLoading(false)
		} catch (error) {
			setErrorMessage(error.message)
			setIsLoading(false)
		}
	}

	useEffect(() => {
		
		fetchSales()
	}, [])

	const handleDelete = async (saleId) => {
		try {
			const result = await deleteSale(saleId)
			if (result.success) {
				setSuccessMessage(`Sale was delete`)
				fetchSales()
			} else {
				console.error(`Failed deleting sale : ${result.responseMessage}`)
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
				<div className="alert alert-info" role="alert">Loading existing sales</div>
			) : (
				<>
					<section className="mt-5 mb-5 container">
						<div className="d-flex justify-content-between mb-3 mt-5">
							<h2>Existing Sales</h2>
						</div>

						<Row>
							<Col md={6} className="d-flex justify-content-end">
								<Link to={"/add-sale"}>
									<FaPlus /> Add Sale
								</Link>
							</Col>
						</Row>

						<table className="table table-bordered table-hover">
							<thead>
								<tr className="text-center">
									<th>No</th>
									<th>Sale Name</th>
									<th>Sale Price</th>
									<th>Actions</th>
								</tr>
							</thead>

							<tbody>
								{sales.map((sale,i) => (
									<tr key={i} className="text-center">
										<td>{i+1}</td>
										<td>{sale.name}</td>
										<td>Rp. {Intl.NumberFormat("ID").format(sale.price)}</td>
										<td className="gap-2">
											<Link to={`/edit-sale/${sale.id}`}>
												<span className="btn btn-warning btn-sm ml-5">
													<FaEdit />
												</span>
											</Link>{" "}
											<button
												className="btn btn-danger btn-sm ml-5"
												onClick={() => handleDelete(sale.id)}>
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