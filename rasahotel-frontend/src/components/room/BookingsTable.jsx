/* eslint-disable react/prop-types */
import { parseISO } from "date-fns";
import { useState, useEffect } from "react";
import DateSlider from "../common/DateSlider";
import { Accordion } from "react-bootstrap";
import { formattedNumber } from "../utils/JSFunction";

export default function BookingsTable({
  bookingInfo,
  handleBookingCancellation,
  handlePayment,
  isLoading,
}) {
  const [filteredBookings, setFilteredBookings] = useState(bookingInfo);

  const filterBookings = (startDate, endDate) => {
    let filtered = bookingInfo;
    filtered = bookingInfo.filter((booking) => {
      const bookingStartDate = parseISO(booking.checkInDate);
      const bookingEndDate = parseISO(booking.checkOutDate);
      return (
        bookingStartDate >= startDate &&
        bookingEndDate <= endDate &&
        bookingEndDate > startDate
      );
    });
    setFilteredBookings(filtered);
  };

  useEffect(() => {
    setFilteredBookings(bookingInfo);
  }, [bookingInfo]);

  return (
    <section className="p-4">
      <Accordion>
        <Accordion.Item eventKey="0">
          <Accordion.Header>Show Filter</Accordion.Header>
          <Accordion.Body>
            <DateSlider
              onDateChange={filterBookings}
              onFilterChange={filterBookings}
            />
          </Accordion.Body>
        </Accordion.Item>
      </Accordion>
      <table className="table table-bordered table-hover shadow-sm">
        <thead className="text-center">
          <tr>
            <th>No</th>
            <th>Room Name</th>
            <th>Check-In Date</th>
            <th>Check-Out Date</th>
            <th>Name</th>
            <th>Email</th>
            <th>Total Payment</th>
            <th>Payment Status</th>
            <th colSpan={2}>Actions</th>
          </tr>
        </thead>
        <tbody className="text-center">
          {filteredBookings.map((booking, index) => (
            <tr key={booking.bookingId}>
              <td>{index + 1}</td>
              <td>{booking.room.name}</td>
              <td>{booking.checkInDate}</td>
              <td>{booking.checkOutDate}</td>
              <td>{booking.customer.name}</td>
              <td>{booking.customer.email}</td>
              <td>{formattedNumber(booking.totalPayment)}</td>
              <td>{booking.paymentRequired ? "Pending" : "Success"}</td>
              <td>
                <button
                  className="btn btn-danger btn-sm"
                  onClick={() => handleBookingCancellation(booking.bookingId)}
                >
                  Cancel
                </button>
                {booking.paymentRequired && (
                  <button
                    className="btn btn-primary btn-sm"
                    onClick={() =>
                      handlePayment(
                        index,
                        booking.bookingConfirmationCode,
                        booking.bookingId
                      )
                    }
                    disabled={isLoading === index}
                  >
                    {isLoading === index ? (
                      <>
                        <span
                          className="spinner-border spinner-border-sm"
                          role="status"
                          aria-hidden="true"
                        ></span>{" "}
                        Loading...
                      </>
                    ) : (
                      "Payment"
                    )}
                  </button>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {filterBookings.length === 0 && (
        <p> No booking found for the selected dates</p>
      )}
    </section>
  );
}
