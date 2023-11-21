import { GridColDef } from "@mui/x-data-grid";
import { useState } from "react";
import {
  managerAddUser,
  updateUserData,
} from "../../../api/manager/ManagerApi";
import { currentUserSetter } from "../../../store/CurrentUser/CurrentUserSlice";
import { updateSelectedUser } from "../../../store/SelectedUser/SelectedUserSlice";
import { useAppDispatch, useAppSelector } from "../../../store/hooks";
import "./userModal.scss";
import { warningToast } from "../../../utils/toasts/userToasts";
import { addEmployee } from "../../../store/UsersTable/UsersTableSlice";

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
    firstName: String | null;
    lastName: String | null;
    email: String | null;
    phone: String | null;
  } | null;
};

export default function UserModal(props: Props) {
  const token = useAppSelector((state) => state.currentUser.token);
  const dispatch = useAppDispatch();

  const [user, setUser] = useState({
    id: props?.user?.id,
    firstName: props?.user?.firstName,
    lastName: props?.user?.lastName,
    email: props?.user?.email,
    phone: props?.user?.phone,
  });

  const handleChange = (field: string, value: string) => {
    setUser({ ...user, [field]: value });
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      if (props.editableMode) {
        await updateUserData(props.id, user, token).then((response) => {
          if (props.self) {
            dispatch(currentUserSetter(response));
          } else {
            dispatch(updateSelectedUser(response));
          }
        });
      } else {
        await managerAddUser(user, token).then((response) => {
          dispatch(addEmployee(response));
        });
      }
    } catch (err: any) {
      warningToast(err.stringify);
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
          {props.columns
            .filter((item) => item.field !== "id" && item.field !== "img")
            .map((column, index) => (
              <div className="item" key={index}>
                <label htmlFor={column.field}>{column.headerName}</label>
                <input
                  id={column.field}
                  type={column.type}
                  placeholder={column.field}
                  name={column.field}
                  onChange={(e) => handleChange(column.field, e.target.value)}
                  value={
                    (user[column.field as keyof typeof user] ?? "") as string
                  }
                />
              </div>
            ))}
          <button>{props.buttonText}</button>
        </form>
      </div>
    </div>
  );
}
