import { Link } from "react-router-dom";
import "./main.scss";

export default function Main() {
  return (
    <div className="main">
      <div className="container center">
        <div className="companyTitle">
          <img className="logo" src="./logo/logo.png" />
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
