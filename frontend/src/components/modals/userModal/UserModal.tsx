import { GridColDef } from "@mui/x-data-grid";
import { useState } from "react";
import {
  managerAddUser,
  updateUserData,
} from "../../../api/manager/ManagerApi";
import { updateCurrentUser } from "../../../store/CurrentUser/CurrentUserSlice";
import { updateSelectedUser } from "../../../store/SelectedUser/SelectedUserSlice";
import { addEmployee } from "../../../store/UsersTable/UsersTableSlice";
import { useAppDispatch, useAppSelector } from "../../../store/hooks";
import "./userModal.scss";
import {
  longWarningToast,
  warningToast,
} from "../../../utils/toasts/userToasts";

type Props = {
  editableMode: boolean;
  self: boolean;
  id: number;
  headerText: string;
  buttonText: string;
  columns: GridColDef[];
  setOpen: React.Dispatch<React.SetStateAction<boolean>>;
  user: {
    id: Number | null;
    firstName: string | null;
    lastName: string | null;
    email: string | null;
    phone: String | null;
  } | null;
};

export default function UserModal(props: Props) {
  const dispatch = useAppDispatch();
  const token = useAppSelector((state) => state.currentUser.token);
  const passwordRegex =
    /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  const [user, setUser] = useState({
    id: props?.user?.id,
    firstName: props?.user?.firstName,
    lastName: props?.user?.lastName,
    email: props?.user?.email,
    phone: props?.user?.phone,
  });
  const [password, setPassword] = useState("");

  const handleChange = (field: string, value: string) => {
    setUser({ ...user, [field]: value });
  };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    console.log(user.phone?.length !== 10)
    if (!emailRegex.test(user.email!)) {
      warningToast("Please type a valid e-mail address.");
      return;
    } else if (!props.editableMode && !passwordRegex.test(password!)) {
      longWarningToast(
        "The password requires a minimum length of 6 characters, at least one uppercase or lowercase letter, requires at least one digit and a special character: @,$,!,%,*,#,?,&"
      );
      return;
    } else if (user.phone?.length !== 10) {
      longWarningToast("The phone number must be 10 characters long.");
      return;
    }
    if (props.editableMode) {
      updateUserData(props.id, user, token).then((response) => {
        if (props.self) {
          dispatch(updateCurrentUser(response));
        } else {
          dispatch(updateSelectedUser(response));
        }
      });
    } else {
      managerAddUser({ ...user, password: password }, token).then(
        (response) => {
          dispatch(addEmployee(response));
        }
      );
    }
    props.setOpen(false);
  };

  return (
    <div className="add">
      <div className="modal">
        <span className="close" onClick={() => props.setOpen(false)}>
          X
        </span>
        <h1>{props.headerText}</h1>
        <form onSubmit={handleSubmit}>
          <div className="inputsContainer">
            {props.columns
              .filter((item) => item.field !== "id")
              .map((column, index) => (
                <div className="item" key={index}>
                  <label className="itemLabel" htmlFor={column.field}>
                    {column.headerName}
                  </label>
                  <input
                    id={column.field}
                    type={column.type}
                    name={column.field}
                    required={!props.editableMode}
                    onChange={(e) =>
                      column.field === "Password"
                        ? (() => {
                            const inputValue = (
                              e?.target as HTMLInputElement
                            )?.value?.replace(/\D/g, "");
                            handleChange(column.field, inputValue);
                          })()
                        : handleChange(column.field, e.target.value)
                    }
                    value={
                      (user[column.field as keyof typeof user] ?? "") as string
                    }
                    maxLength={column.field === "phone" ? 10 : 20}
                  />
                </div>
              ))}
            {!props.editableMode && (
              <div className="item" key={5}>
                <label className="itemLabel" htmlFor={"password"}>
                  Password
                </label>
                <input
                  id={"password"}
                  type={"text"}
                  name={"Password"}
                  required={!props.editableMode}
                  onChange={(e) => setPassword(e.target.value)}
                  maxLength={20}
                />
              </div>
            )}
          </div>
          <button className="updateButton">{props.buttonText}</button>
        </form>
      </div>
    </div>
  );
}
