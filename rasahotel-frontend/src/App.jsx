import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import AuthProvider from "./components/auth/AuthProvider";
import RequireAuth from "./components/auth/RequireAuth";
import NavigationBar from "./components/layout/NavigationBar";
import Footer from "./components/layout/Footer";
import EditRoom from "./components/room/EditRoom";
import AddRoom from "./components/room/AddRoom";
import Login from "./components/auth/Login";
import Registration from "./components/auth/Registration";
import Admin from "./components/admin/Admin";
import Profile from "./components/auth/Profile";
import Home from "./components/home/Home";
import Checkout from "./components/booking/Checkout";
import RoomList from "./components/room/RoomsList";
import ExistingRooms from "./components/room/ExistingRooms";
import FindBooking from "./components/room/FindBooking";
import Bookings from "./components/room/Bookings";
import BookingSuccess from "./components/room/BookingSuccess";
import { ThemeProvider } from "./hooks/useThemeContext";
import "./scss/bootstrap.scss";
import AddCategory from "./components/category/AddCategory";
import ExistingSales from "./components/category/ExistingSales";
import ExistingCategories from "./components/category/ExistingCategories";
import AddSale from "./components/category/AddSale";
import EditSale from "./components/category/EditSale";
import EditCategory from "./components/category/EditCategory";
import Report from "./components/admin/Report";
import ThemeIcon from "./context/ThemeProvider";

export default function App() {
  return (
    <AuthProvider>
      <ThemeProvider>
        <main>
          <Router>
            <NavigationBar />
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/edit-room/:roomId" element={<EditRoom />} />
              <Route path="/existing-rooms" element={<ExistingRooms />} />
              <Route path="/add-room" element={<AddRoom />} />
              <Route path="/existing-categories" element={<ExistingCategories />} />
              <Route path="/add-category" element={<AddCategory />} />
              <Route path="/edit-category/:categoryId" element={<EditCategory />} />
              <Route path="/existing-sales" element={<ExistingSales />} />
              <Route path="/add-sale" element={<AddSale />} />
              <Route path="/edit-sale/:saleId" element={<EditSale />} />

              <Route
                path="/book-room/:roomId"
                element={
                  <RequireAuth>
                    <Checkout />
                  </RequireAuth>
                }
              />
              <Route path="/browse-all-rooms" element={<RoomList />} />

              <Route path="/admin" element={<Admin />} />
              <Route path="/booking-success" element={<BookingSuccess />} />
              <Route path="/existing-bookings" element={<Bookings />} />
              <Route path="/report" element={<Report />} />
              <Route path="/find-booking" element={<FindBooking />} />

              <Route path="/login" element={<Login />} />
              <Route path="/register" element={<Registration />} />

              <Route path="/profile" element={<Profile />} />
              <Route path="/logout" element={<Home />} />
            </Routes>
          </Router>
          <ThemeIcon />
          <Footer />
        </main>
      </ThemeProvider>
    </AuthProvider>
  );
}
