import { Link } from "react-router-dom";


export default function MainHeader() {
  return (
    <header className="header-banner text-center">
      <div className="overlay">
      </div>
      <div className="animated-texts overlay-content brand-text">
        <img src={"./bg1.png"} alt="" width={"700px"} height={"300px"} className="brand-image-border" />
        <h1>
          <span>RASA HOTEL</span>
        </h1>
          <h4 className="brand-slogan">Experience the Best Reservation in Town</h4>
        <br />
      <Link className="btn btn-secondary btn-browse-room" to={"/browse-all-rooms"}>Browse All Rooms</Link>
      </div>
    </header>
  );
}
