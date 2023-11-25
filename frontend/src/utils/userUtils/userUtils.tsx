import { Link } from "react-router-dom";
import LogOut from "../../components/icons/LogOut";
import { resetUserSetter } from "../../store/CurrentUser/CurrentUserSlice";
import { useAppDispatch } from "../../store/hooks";
import { successToast } from "../toasts/userToasts";

export const SignOut = () => {
  const dispatch = useAppDispatch();

  const handleSignOut = () => {
    dispatch(resetUserSetter());
    successToast("Goodbye!");
  };

  return (
    <Link to="/" onClick={handleSignOut}>
      <LogOut />
    </Link>
  );
};
