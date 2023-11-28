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
  const dispatch = useAppDispatch();
  const token = useAppSelector((state) => state.currentUser.token);
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

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (props.editableMode) {
      updateUserData(props.id, user, token).then((response) => {
        if (props.self) {
          dispatch(updateCurrentUser(response));
        } else {
          dispatch(updateSelectedUser(response));
        }
      });
    } else {
      managerAddUser(user, token).then((response) => {
        dispatch(addEmployee(response));
      });
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
                    onChange={(e) => handleChange(column.field, e.target.value)}
                    value={
                      (user[column.field as keyof typeof user] ?? "") as string
                    }
                  />
                </div>
              ))}
          </div>
          <button className="updateButton">{props.buttonText}</button>
        </form>
      </div>
    </div>
  );
}
