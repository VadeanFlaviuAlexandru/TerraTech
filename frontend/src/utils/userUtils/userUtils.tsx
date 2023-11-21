import Cookies from "js-cookie";
import { Link } from "react-router-dom";
import { resetUserSetter } from "../../store/CurrentUser/CurrentUserSlice";
import { useAppDispatch } from "../../store/hooks";
import { successToast } from "../toasts/userToasts";

export const SignOut = () => {
  const dispatch = useAppDispatch();

  const handleSignOut = () => {
    dispatch(resetUserSetter());
    Cookies.remove("JWT_Token");
    successToast("Goodbye!");
  };

  return (
    <Link to="/" onClick={handleSignOut} className="button">
      Sign Out
    </Link>
  );
};
