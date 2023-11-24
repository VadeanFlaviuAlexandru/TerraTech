import { Link } from "react-router-dom";
import "./main.scss";

export default function Main() {
  return (
    <div className="main">
      <div className="container center">
        <div className="companyTitle">
          <img className="logo" src="./public/logo/logo.png" />
        </div>
        <div className="description">
          <h4>
            TerraTech Innovations stands as the world's foremost conglomerate,
            leading the charge in innovative solutions across a vast spectrum of
            industries. Our unwavering commitment to excellence and a relentless
            pursuit of cutting-edge technology have propelled us to the pinnacle
            of global recognition.
          </h4>
          <h4>
            From technology to healthcare, from nourishing the world to
            engineering vessels, we are defying gravity, innovating chemistry,
            and championing social responsibility. TerraTech Innovations and its
            family of renowned subsidiaries are the architects of tomorrow's
            achievements, united in a relentless quest to shape a brighter, more
            prosperous future.
          </h4>
        </div>
        <div className="buttons">
          <Link to="/login" className="button">
            Sign in
          </Link>
          <Link to="/signup" className="button">
            Sign up
          </Link>
        </div>
      </div>
    </div>
  );
}
