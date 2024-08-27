/* eslint-disable react/prop-types */
import { useState } from "react"
import { Row, Button } from "react-bootstrap"
import RoomPaginator from "../room/RoomPaginator"
import RoomCard from "../room/RoomCard"

export default function RoomSearchResults({ results, onClearSearch }) {

    const [currentPage, setCurrentPage] = useState(1)
	const resultsPerPage = 3
	const totalResults = results.length
	const totalPages = Math.ceil(totalResults / resultsPerPage)

	const handlePageChange = (pageNumber) => {
		setCurrentPage(pageNumber)
	}

	const startIndex = (currentPage - 1) * resultsPerPage
	const endIndex = startIndex + resultsPerPage
	const paginatedResults = results.slice(startIndex, endIndex)

	return (
		<>
			{results.length > 0 ? (
				<>
					<h5 className="text-center mt-5">Search Results</h5>
					<Row>
						{paginatedResults.map((room) => (
							<RoomCard key={room.id} room={room} />
						))}
					</Row>
					<Row>
						{totalResults > resultsPerPage && (
							<RoomPaginator
								currentPage={currentPage}
								totalPages={totalPages}
								onPageChange={handlePageChange}
							/>
						)}
						<Button variant="secondary" onClick={onClearSearch}>
							Clear Search
						</Button>
					</Row>
				</>
			) : (
				<p></p>
			)}
		</>
	)
}