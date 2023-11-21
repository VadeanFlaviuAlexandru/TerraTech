import { GridColDef } from "@mui/x-data-grid";
import { useEffect, useState } from "react";
import { useLocation, useParams } from "react-router-dom";
import {
  Legend,
  Line,
  LineChart,
  ResponsiveContainer,
  Tooltip,
  XAxis,
  YAxis,
} from "recharts";
import {
  changeStatus,
  deleteReport,
  fetchUserData,
} from "../../api/manager/ManagerApi";
import ReportModal from "../../components/modals/reportModal/ReportModal";
import UserModal from "../../components/modals/userModal/UserModal";
import {
  currentUserSetter,
  deleteCurrentReport,
} from "../../store/CurrentUser/CurrentUserSlice";
import {
  deleteSelectedReport,
  resetSelectedUser,
  selectedUserSetter,
  updateSelectedUser,
} from "../../store/SelectedUser/SelectedUserSlice";
import { useAppDispatch, useAppSelector } from "../../store/hooks";
import { columnsReportModal } from "../../utils/data/columns";
import { warningToast } from "../../utils/toasts/userToasts";
import "./user.scss";

const columns: GridColDef[] = [
  { field: "id", headerName: "ID", width: 90 },
  {
    field: "firstName",
    headerName: "First name",
    width: 150,
    type: "string",
  },
  {
    field: "lastName",
    headerName: "Last name",
    width: 150,
    type: "string",
  },
  {
    field: "email",
    headerName: "E-mail",
    width: 150,
    type: "string",
  },
  {
    field: "phone",
    headerName: "Phone",
    width: 100,
    type: "string",
  },
  {
    field: "phone",
    headerName: "Phone",
    width: 100,
    type: "string",
  },
];

const User = () => {
  const dispatch = useAppDispatch();
  const currentUser = useAppSelector((state) => state.currentUser);
  const selectedUser = useAppSelector((state) => state.selectedUser);
  const [open, setOpen] = useState(false);
  const [openReport, setOpenReport] = useState(false);
  const location = useLocation();
  const id = location?.state?.id || useParams()?.id;
  const [reportId, setReportId] = useState(0);
  const [report, setReport] = useState({
    id: reportId,
    productId: "",
    description: "",
    productName: "",
    peopleSoldTo: 0,
    peopleNotifiedAboutProduct: 0,
  });
  const monthNameToNumber: { [key: string]: number } = {
    JANUARY: 0,
    FEBRUARY: 1,
    MARCH: 2,
    APRIL: 3,
    MAY: 4,
    JUNE: 5,
    JULY: 6,
    AUGUST: 7,
    SEPTEMBER: 8,
    OCTOBER: 9,
    NOVEMBER: 10,
    DECEMBER: 11,
  };

  const processedData = (
    id === currentUser.user.id
      ? currentUser.chartInfo.data
      : selectedUser.chartInfo.data
  ).map((dataPoint) => {
    const monthNumber = monthNameToNumber[dataPoint.name];
    const isFutureMonth = new Date().getMonth() < monthNumber;

    return {
      ...dataPoint,
      peopleNotifiedAboutProduct: isFutureMonth
        ? null
        : dataPoint.peopleNotifiedAboutProduct || 0,
      peopleSoldTo: isFutureMonth ? null : dataPoint.peopleSoldTo || 0,
    };
  });

  function getRandomColor() {
    return "#" + Math.floor(Math.random() * 16777215).toString(16);
  }

  useEffect(() => {
    if (id != currentUser.user.id) {
      const fetchData = async () => {
        try {
          await fetchUserData(id, currentUser.token).then((response) => {
            dispatch(selectedUserSetter(response));
          });
        } catch (err: any) {
          warningToast(err.stringify);
        }
      };
      fetchData();
    }
    return () => {
      resetSelectedUser();
    };
  }, [id]);

  return (
    <div className="user">
      <div className="single">
        <div className="view">
          <div className="info">
            <div className="topInfo">
              <h1>
                {id == currentUser.user.id
                  ? `${currentUser.user.firstName} ${currentUser.user.lastName}`
                  : `${selectedUser.user.firstName} ${selectedUser.user.lastName}`}
              </h1>
              {currentUser.user.role !== "ROLE_EMPLOYEE" && (
                <button onClick={() => setOpen(true)}>Update</button>
              )}
            </div>
            <div className="details">
              {Object.entries(
                id == currentUser.user.id ? currentUser.user : selectedUser.user
              )
                .filter(([key]) => key !== "password")
                .map(([key, value]) => (
                  <div className="item" key={key}>
                    <span className="itemTitle">{key}: </span>
                    <span className="itemValue">
                      {value === true
                        ? "Active"
                        : value === false
                        ? "Inactive"
                        : value}
                    </span>
                    {(value === false || value === true) &&
                      currentUser.user.role !== "ROLE_EMPLOYEE" && (
                        <button
                          onClick={() => {
                            changeStatus(
                              id == currentUser.user.id
                                ? currentUser.user.id
                                : selectedUser.user.id,
                              currentUser.token
                            ).then((response) => {
                              if (id == currentUser.user.id) {
                                dispatch(currentUserSetter(response));
                              } else {
                                dispatch(updateSelectedUser(response));
                              }
                            });
                          }}
                        >
                          {value === true
                            ? "change status to inactive"
                            : value === false && "change status to active"}
                        </button>
                      )}
                  </div>
                ))}
            </div>
          </div>
          <hr />
          <div className="chart">
            <ResponsiveContainer width="100%" height="100%">
              <LineChart
                width={500}
                height={300}
                data={processedData}
                margin={{
                  top: 5,
                  right: 30,
                  left: 20,
                  bottom: 5,
                }}
              >
                <XAxis dataKey="name" />
                <YAxis />
                <Tooltip />
                <Legend />
                {(id == currentUser.user.id
                  ? currentUser.chartInfo
                  : selectedUser.chartInfo
                ).dataKeys.map((dataKey) => (
                  <Line
                    type="monotone"
                    dataKey={dataKey.name}
                    stroke={getRandomColor()}
                  />
                ))}
              </LineChart>
            </ResponsiveContainer>
          </div>
        </div>
        <div className="activities">
          {(id == currentUser.user.id
            ? currentUser?.reports
            : selectedUser?.reports
          ).length > 0 ? (
            <>
              <h2>Latest Activities</h2>
              <ul>
                {(id == currentUser.user.id
                  ? currentUser?.reports
                  : selectedUser?.reports
                ).map((report, index) => (
                  <li key={index}>
                    <div className="reportCard">
                      <p>{report.description}</p>
                      <time>
                        Created {report.createDate} for {report.productName}
                      </time>
                      <div className="reportCardButtons">
                        <button
                          onClick={() => {
                            setReportId(report.id);
                            setReport(report);
                            setOpenReport(true);
                          }}
                        >
                          edit
                        </button>
                        <button
                          onClick={() => {
                            deleteReport(report.id, currentUser.token).then(
                              (response) => {
                                if (response.ok) {
                                  if (id === currentUser.user.id) {
                                    dispatch(deleteCurrentReport(report.id));
                                  } else {
                                    dispatch(deleteSelectedReport(report.id));
                                  }
                                }
                              }
                            );
                          }}
                        >
                          delete
                        </button>
                      </div>
                    </div>
                  </li>
                ))}
              </ul>
            </>
          ) : (
            "no reports made by this user"
          )}
        </div>
      </div>
      {open && (
        <UserModal
          editableMode={true}
          self={id === currentUser.user.id}
          headerText="Update credentials"
          buttonText="Update"
          columns={columns}
          setOpen={setOpen}
          user={selectedUser.user}
          id={selectedUser.user.id}
        />
      )}
      {openReport && (
        <ReportModal
          self={id === currentUser.user.id}
          headerText="Update report information"
          buttonText="Update"
          columns={columnsReportModal}
          setOpen={setOpenReport}
          report={report}
          id={reportId}
        />
      )}
    </div>
  );
};

export default User;
