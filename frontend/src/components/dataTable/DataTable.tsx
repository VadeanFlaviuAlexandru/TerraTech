import { DataGrid, GridColDef, GridToolbar } from "@mui/x-data-grid";
import { useEffect } from "react";
import { Link, useLocation } from "react-router-dom";
import { deleteEmployee } from "../../api/manager/ManagerApi";
import { deleteProduct } from "../../api/product/ProductApi";
import { AllManagersState } from "../../store/AllManagers/AllManagersSlice";
import {
  ProductsTableState,
  removeProduct,
} from "../../store/ProductsTable/ProductTableSlice";
import {
  UsersTableState,
  removeEmployee,
  resetUsersTable,
} from "../../store/UsersTable/UsersTableSlice";
import { useAppDispatch, useAppSelector } from "../../store/hooks";
import DeleteIcon from "../icons/DeleteIcon";
import ManagerProductsIcon from "../icons/ManagerProductsIcon";
import ManagerUsersIcon from "../icons/ManagerUsersIcon";
import SmallEditIcon from "../icons/SmallEditIcon";
import "./dataTable.scss";

type Props = {
  columns: GridColDef[];
  userTable: boolean;
  rows:
    | UsersTableState["users"]
    | ProductsTableState["products"]
    | AllManagersState["managers"];
  slug: string;
};

export default function DataTable(props: Props) {
  const dispatch = useAppDispatch();
  const currentUser = useAppSelector((state) => state.currentUser);
  const location = useLocation();

  const handleDelete = (id: number) => {
    if (props.userTable) {
      deleteEmployee(id).then((response) => {
        if (response.ok) {
          dispatch(removeEmployee(id));
        }
      });
    } else {
      deleteProduct(id).then((response) => {
        if (response.ok) {
          dispatch(removeProduct(id));
        }
      });
    }
  };

  useEffect(() => {
    return () => {
      dispatch(resetUsersTable());
    };
  }, []);

  const actionColumn: GridColDef = {
    field: "action",
    headerName: "Actions",
    width: 200,
    renderCell: (params) => {
      return (
        <div className="action">
          <Link
            state={{ id: params.row.id }}
            to={`/dashboard/${props.slug}/${params.row.id}`}
          >
            <SmallEditIcon color={"#242526"} />
          </Link>
          <div className="delete" onClick={() => handleDelete(params.row.id)}>
            <DeleteIcon color={"#242526"} />
          </div>
          {currentUser.user.role === "ROLE_ADMIN" &&
            !location.pathname.includes("manager") && (
              <>
                <Link
                  state={{ id: params.row.id }}
                  to={`/dashboard/users/manager/${params.row.id}`}
                >
                  <ManagerUsersIcon color={"#242526"} />
                </Link>
                <Link
                  state={{ id: params.row.id }}
                  to={`/dashboard/products/manager/${params.row.id}`}
                >
                  <ManagerProductsIcon color={"#242526"} />
                </Link>
              </>
            )}
        </div>
      );
    },
  };

  return (
    <div className="dataTable">
      <DataGrid
        className="dataGrid"
        rows={props.rows}
        columns={[...props.columns, actionColumn]}
        initialState={{
          pagination: {
            paginationModel: {
              pageSize: 8,
            },
          },
        }}
        pageSizeOptions={[5]}
        disableRowSelectionOnClick
        slots={{ toolbar: GridToolbar }}
        slotProps={{
          toolbar: {
            showQuickFilter: true,
            quickFilterProps: { debounceMs: 300 },
          },
        }}
        disableColumnFilter
        disableColumnSelector
        disableDensitySelector
        checkboxSelection={false}
      />
    </div>
  );
}
