import { Col, Container, Row } from "react-bootstrap"

export default function Footer() {

    let today = new Date()

    return (
        <footer className="py-3 mt-lg-5">
			<Container>
				<Row>
					<Col xs={12} md={12} className="text-center">
						<p className="mb-0"> &copy; {today.getFullYear()} Rasa Hotel</p>
					</Col>
				</Row>
			</Container>
		</footer>
    )
}