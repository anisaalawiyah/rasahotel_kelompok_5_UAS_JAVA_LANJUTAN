import { useEffect, useState } from "react"
import { deleteRoom, getRooms } from "../utils/fecthApi"
import { Col, Row } from "react-bootstrap"
import RoomFilter from "../common/RoomFilter"
import RoomPaginator from "../common/RoomPaginator"
import { FaEdit, FaPlus, FaTrashAlt } from "react-icons/fa"
import { Link } from "react-router-dom"

export default function ExistingRooms() {

    const [rooms, setRooms] = useState([{ id: "", name: "", description: "", price: "", categoryId: null }])
	const [currentPage, setCurrentPage] = useState(1)
	const [roomsPerPage] = useState(8)
	const [isLoading, setIsLoading] = useState(false)
	const [filteredRooms, setFilteredRooms] = useState([{ id: "", roomType: "", roomPrice: "" }])
	const [selectedRoomType, setSelectedRoomType] = useState("")
	const [errorMessage, setErrorMessage] = useState("")
	const [successMessage, setSuccessMessage] = useState("")

	
	const fetchRooms = async () => {
		setIsLoading(true)
		try {
			const result = await getRooms()
			setRooms(result)
			setIsLoading(false)
		} catch (error) {
			setErrorMessage(error.message)
			setIsLoading(false)
		}
	}

	useEffect(() => {
		fetchRooms()
	},[])

	useEffect(() => {
		if (selectedRoomType === "") {
			setFilteredRooms(rooms)
		} else {
			const filteredRooms = rooms.filter((room) => room.category.name === selectedRoomType)
			setFilteredRooms(filteredRooms)
		}
		setCurrentPage(1)
	}, [rooms, selectedRoomType])

	const handlePaginationClick = (pageNumber) => {
		setCurrentPage(pageNumber)
	}

	const handleDelete = async (roomId) => {
		try {
			const result = await deleteRoom(roomId)
			if (result.success) {
				setSuccessMessage(`Room was delete`)
				fetchRooms()
			} else {
				console.error(`Failed deleting room : ${result.responseMessage}`)
			}
		} catch (error) {
			setErrorMessage(error.message)
		}
		setTimeout(() => {
			setSuccessMessage("")
			setErrorMessage("")
		}, 3000)
	}

	const calculateTotalPages = (filteredRooms, roomsPerPage, rooms) => {
		const totalRooms = filteredRooms.length > 0 ? filteredRooms.length : rooms.length
		return Math.ceil(totalRooms / roomsPerPage)
	}

	const indexOfLastRoom = currentPage * roomsPerPage
	const indexOfFirstRoom = indexOfLastRoom - roomsPerPage
	const currentRooms = filteredRooms.slice(indexOfFirstRoom, indexOfLastRoom)

	return (
		<>
			<div className="container col-md-8 col-lg-6">
				{successMessage && <p className="alert alert-success mt-5">{successMessage}</p>}

				{errorMessage && <p className="alert alert-danger mt-5">{errorMessage}</p>}
			</div>

			{isLoading ? (
				<div className="alert alert-info" role="alert">Loading existing rooms</div>
			) : (
				<>
					<section className="mt-5 mb-5 container">
						<div className="d-flex justify-content-between mb-3 mt-5">
							<h2>Existing Rooms</h2>
						</div>

						<Row>
							<Col md={6} className="mb-2 md-mb-0">
								{/* <RoomFilter data={rooms} setFilteredData={setFilteredRooms} /> */}
							</Col>
							<Col md={6} className="d-flex justify-content-end">
								<Link to={"/add-room"}>
									<FaPlus /> Add Room
								</Link>
							</Col>

						</Row>

						<table className="table table-bordered table-hover">
							<thead>
								<tr className="text-center">
									<th>No</th>
									<th>Room Name</th>
									<th>Room Price</th>
									<th>Actions</th>
								</tr>
							</thead>

							<tbody>
								{currentRooms.map((room,i) => (
									<tr key={i} className="text-center">
										<td>{i+1}</td>
										<td>{room.name}</td>
										<td>Rp. {Intl.NumberFormat("ID").format(room.price)}</td>
										<td className="gap-2">
											<Link to={`/edit-room/${room.id}`}>
												<span className="btn btn-warning btn-sm ml-5">
													<FaEdit />
												</span>
											</Link>{" "}
											<button
												className="btn btn-danger btn-sm ml-5"
												onClick={() => handleDelete(room.id)}>
												<FaTrashAlt />
											</button>
										</td>
									</tr>
								))}
							</tbody>
						</table>
						<RoomPaginator
							currentPage={currentPage}
							totalPages={calculateTotalPages(filteredRooms, roomsPerPage, rooms)}
							onPageChange={handlePaginationClick}
						/>
					</section>
				</>
			)}
		</>
	)
}