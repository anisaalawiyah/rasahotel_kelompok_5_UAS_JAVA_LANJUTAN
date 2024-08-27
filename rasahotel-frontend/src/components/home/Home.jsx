import MainHeader from "../layout/MainHeader";
import RoomCarousel from "../common/RoomCarousel";
import Parallax from "../common/Parallax";
import HotelService from "../common/HotelService";
import PageDivider from "../layout/PageDivider";

export default function Home() {

  return (
    <section>
      {/* {message && <p className="alert alert-warning px-5" role="alert">{message}</p>} */}
      <MainHeader />
      <PageDivider />
      <Parallax />
      <RoomCarousel />
      <PageDivider />
      <HotelService />
      <PageDivider />
      <Parallax />
      <PageDivider />
      <RoomCarousel />
    </section>
  );
}
