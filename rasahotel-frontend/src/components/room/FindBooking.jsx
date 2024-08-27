import { cancelBooking, checkout, checkoutSuccess, getAllBookings } from "../utils/fecthApi";
import { useState, useEffect } from "react";
import Header from "../common/Header";
import BookingsTable from "./BookingsTable";

export default function FindBooking() {
  const [paymentLoadingIndex, setPaymentLoadingIndex] = useState();
  const [bookingInfo, setBookingInfo] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState("");
  const userId = localStorage.getItem("userId");

  useEffect(() => {
    setTimeout(async () => {
      await getAllBookings()
        .then((data) => {
          const booking = data.filter(
            (booking) => booking.customer.email === userId
          );
          setBookingInfo(booking);
          setIsLoading(false);
        })
        .catch((error) => {
          setError(error.message);
          setIsLoading(false);
        });
    }, 1000);
  },[userId]);

  const handleBookingCancellation = async (bookingId) => {
    try {
      await cancelBooking(bookingId);
      window.location.reload()
    } catch (error) {
      setError(error.message);
    }
  };

  const handlePayment = async (idx, code, bookingId) => {
    setPaymentLoadingIndex(idx)
    await checkout(code).then((res) => {
      pay(res.transactionToken, bookingId)
    });

  };
  
  const pay = (transactionToken,bookingId) => {
    
    window.snap.pay(transactionToken, {
      // Optional
      onSuccess: async function (result) {
        checkoutSuccess(bookingId);
        window.location.reload()
        console.log("OnSuccess : " + JSON.stringify(result, null, 2));
      },
      // Optional
      onPending: function (result) {
        console.log("OnPending : " + JSON.stringify(result, null, 2));
      },
      // Optional
      onError: function () {},
      onClose: function () {
        localStorage.removeItem("transactionToken")
        window.location.reload()
      },
    });
  }

  return (
    <section style={{ backgroundColor: "whitesmoke" }}>
      {bookingInfo.length == 0 ? (
        <div className="alert alert-warning" role="alert">
          Booking Data Not Available
        </div>
      ) : (
        <>
          <Header title={"Existing Bookings"} />
          {error && (
            <div className="alert alert-danger" role="alert">
              {error}
            </div>
          )}
          {isLoading ? (
            <div className="alert alert-check" role="alert">
              Loading existing bookings
            </div>
          ) : (
            <BookingsTable
              bookingInfo={bookingInfo}
              handleBookingCancellation={handleBookingCancellation}
              handlePayment={handlePayment}
              isLoading={paymentLoadingIndex}
            />
          )}
        </>
      )}
    </section>
  );
}
