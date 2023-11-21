import "./navbar.scss";
import { useAppSelector } from "../../store/hooks";
import { SignOut } from "../../utils/userUtils/userUtils";

export default function Navbar() {
  const currentUser = useAppSelector((state) => state.currentUser);
  return (
    <div className="navbar">
      <div className="logo">
        <img src=" " alt="" />
        <span>TerraTech Innovations</span>
      </div>
      <div className="user">
        <span>
          {currentUser.user.firstName} {currentUser.user.lastName}
        </span>
        <SignOut />
      </div>
    </div>
  );
}
