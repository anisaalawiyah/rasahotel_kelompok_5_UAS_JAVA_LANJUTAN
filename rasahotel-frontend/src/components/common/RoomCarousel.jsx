import { useEffect, useState } from "react"
import { getRooms } from "../utils/fecthApi"
import { Link } from "react-router-dom"
import { Card, Carousel, Col, Container, Row } from "react-bootstrap"
import { FaExclamationTriangle } from "react-icons/fa"

export default function RoomCarousel() {

    const [rooms, setRooms] = useState([{ id: "", name: "", desc: "", price: "", category: null, photo: null }])
	const [errorMessage, setErrorMessage] = useState("")
	const [isLoading, setIsLoading] = useState(false)

	useEffect(() => {
		setIsLoading(true)
		getRooms()
			.then((data) => {
				setRooms(data)
				setIsLoading(false)
			})
			.catch((error) => {
				setErrorMessage(error.message)
				setIsLoading(false)
			})
	}, [])

	if (isLoading) {
		return <div className="mb-5 mt-5 text-info">Loading rooms....</div>
	}
	if (errorMessage) {
		return <div className="text-center text-danger mb-5 mt-5"><FaExclamationTriangle />{" "}{errorMessage}</div>
	}

	return (
		<section className="border-bottom">
			<Container>
				<Carousel controls={false} interval={3000} fade>
					{[...Array(Math.ceil(rooms.length / 4))].map((_, index) => (
						<Carousel.Item key={index}>
							<Row>
								{rooms.slice(index * 4, index * 4 + 4).map((room) => (
									<Col key={room.id} className="mt-4 mb-4" xs={12} md={6} lg={3}>
										<Card className="room-card">
											<Link to={`/book-room/${room.id}`}>
												<Card.Img
													variant="top"
													src={`data:image/png;base64, ${room.photo}`}
													alt="Room Photo"
													className="w-100"
													style={{ height: "200px" }}
												/>
											</Link>
											<Card.Body>
												<Card.Title className="hotel-color">{room.name}</Card.Title>
												<Card.Title className="room-price">Rp {Intl.NumberFormat("ID").format(room.price)}/night</Card.Title>
												<div className="flex-shrink-0">
													<Link to={`/book-room/${room.id}`} className="btn btn-hotel btn-sm">
														Book Now
													</Link>
												</div>
											</Card.Body>
										</Card>
									</Col>
								))}
							</Row>
						</Carousel.Item>
					))}
				</Carousel>
			</Container>
		</section>
	)
}