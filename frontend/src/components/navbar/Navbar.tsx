import { useAppSelector } from "../../store/hooks";
import { SignOut } from "../../utils/userUtils/userUtils";
import "./navbar.scss";

export default function Navbar() {
  const currentUser = useAppSelector((state) => state.currentUser);
  return (
    <div className="navbar">
      <span>
        Welcome, {currentUser?.user?.firstName} {currentUser?.user?.lastName}.
      </span>
      <SignOut />
    </div>
  );
}
