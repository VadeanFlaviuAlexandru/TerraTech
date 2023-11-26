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
import {
  columnsReportModal,
  columnsUserProfile,
} from "../../utils/data/columns";
import {
  getRandomColor,
  processedData,
  roleMapping,
} from "../../utils/data/data";
import "./user.scss";
import { UserInfoMappings } from "../../utils/data/data";
const User = () => {
  const dispatch = useAppDispatch();
  const location = useLocation();
  const id = location?.state?.id || useParams()?.id;
  const currentUser = useAppSelector((state) => state.currentUser);
  const selectedUser = useAppSelector((state) => state.selectedUser);
  const [open, setOpen] = useState(false);
  const [openReport, setOpenReport] = useState(false);
  const [reportId, setReportId] = useState(0);
  const [report, setReport] = useState({
    id: reportId,
    productId: "",
    description: "",
    productName: "",
    peopleSoldTo: 0,
    peopleNotifiedAboutProduct: 0,
  });

  useEffect(() => {
    if (id != currentUser.user.id) {
      const fetchData = () => {
        fetchUserData(id, currentUser.token).then((response) => {
          dispatch(selectedUserSetter(response));
        });
      };
      fetchData();
    }
    return () => {
      resetSelectedUser();
    };
  }, [id]);

  return (
    <div className="userContainer">
      <div className="infoContainer">
        <div className="leftContainer">
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
              id === currentUser.user.id ? currentUser.user : selectedUser.user
            )
              .filter(([key]) => key !== "password" && key !== "id")
              .map(([key, value]) => (
                <div className="item" key={key}>
                  <span className="itemTitle">{UserInfoMappings[key]}: </span>
                  <span
                    className={
                      value === true
                        ? "itemValueActive"
                        : value === false
                        ? "itemValueInactive"
                        : "itemValue"
                    }
                  >
                    {roleMapping[value.toString()] !== undefined
                      ? roleMapping[value.toString()]
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
          <div className="chart">
            <ResponsiveContainer width="100%" height="100%">
              <LineChart
                width={500}
                height={300}
                data={
                  id === currentUser.user.id
                    ? processedData(currentUser.chartInfo.data)
                    : processedData(selectedUser.chartInfo.data)
                }
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
        <div className="rightContainer">
          {(id == currentUser.user.id
            ? currentUser?.reports
            : selectedUser?.reports
          ).length > 0 ? (
            <>
              <h2>Latest reports</h2>
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
          columns={columnsUserProfile}
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
