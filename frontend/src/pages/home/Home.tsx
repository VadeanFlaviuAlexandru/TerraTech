import { useEffect, useState } from "react";
import "react-toastify/dist/ReactToastify.css";
import {
  fetchAllManagers,
  fetchStatistics,
} from "../../api/statistics/StatisticsApi";
import ChartBox from "../../components/charts/chartBox/ChartBox";
import PieChartBox from "../../components/charts/pieChartBox/PieChartBox";
import TopBox from "../../components/charts/topBox/TopBox";
import LoadingIcon from "../../components/icons/LoadingIcon";
import {
  allManagersSetter,
  resetAllManagers,
} from "../../store/AllManagers/AllManagersSlice";
import {
  resetStatistics,
  statisticsSetter,
} from "../../store/Statistics/StatisticsSlice";
import { useAppDispatch, useAppSelector } from "../../store/hooks";
import { ForceSignOut } from "../../utils/userUtils/userUtils";
import "./home.scss";

const Home = () => {
  const dispatch = useAppDispatch();
  const statistics = useAppSelector((state) => state.statistics);
  const currentUser = useAppSelector((state) => state.currentUser);
  const allManagers = useAppSelector((state) => state.allManagers.managers);
  const [loading, setLoading] = useState(true);
  const [selected, setSelected] = useState(-1);
  const [fetch, setFetch] = useState(false);

  useEffect(() => {
    if (selected !== -1) {
      fetchStatistics(selected, currentUser?.token).then((response) => {
        dispatch(statisticsSetter(response));
        setLoading(false);
      });
      setFetch(false);
    }
  }, [fetch]);

  useEffect(() => {
    if (currentUser?.user?.role === "ROLE_ADMIN") {
      if (allManagers?.length === 0) {
        fetchAllManagers(currentUser?.token).then((response) => {
          dispatch(allManagersSetter(response));
          if (response.length !== 0) {
            setSelected(response[0].id);
          }
        });
      } else if (allManagers?.length !== 0) {
        if (selected === -1) {
          setSelected(allManagers[0].id);
        }
        setFetch(true);
        setLoading(true);
      }
    } else {
      fetchStatistics(currentUser?.user?.id, currentUser?.token).then(
        (response) => {
          dispatch(statisticsSetter(response));
          setLoading(false);
        }
      );
    }
    return () => {
      dispatch(resetStatistics());
      setLoading(true);
    };
  }, [selected]);

  useEffect(() => {
    if (currentUser.token === "ROLE_EMPLOYEE") {
      ForceSignOut();
    }
    return () => {
      dispatch(resetAllManagers());
    };
  }, []);

  return (
    <>
      {!loading && allManagers.length > 0 && (
        <div className="dropdownContainer">
          <label>Select a manager:</label>
          <select
            value={selected}
            onChange={(e) => setSelected(parseInt(e.target.value))}
          >
            {allManagers.map(({ id, firstName, lastName }) => (
              <option key={id} value={id}>
                {firstName}
                {lastName}
              </option>
            ))}
          </select>
        </div>
      )}
      {loading ? (
        allManagers?.length === 0 && currentUser.user.role === "ROLE_ADMIN" ? (
          <div className="noManagers">
            There are no managers to show the statistics
          </div>
        ) : (
          <div className="gears">
            <div className="animations">
              <span>Loading statistics...</span>
              <LoadingIcon />
            </div>
          </div>
        )
      ) : (
        <div className="home">
          <div className="box box1">
            <TopBox {...statistics} />
          </div>
          <div className="box box2">
            <ChartBox {...statistics.totalUsers} />
          </div>
          <div className="box box3">
            <ChartBox {...statistics.totalProducts} />
          </div>
          <div className="box box4">
            <PieChartBox />
          </div>
          <div className="box box5">
            <ChartBox {...statistics.totalNotifiedPeople} />
          </div>
          <div className="box box6">
            <ChartBox {...statistics.totalRevenueThisYear} />
          </div>
        </div>
      )}
    </>
  );
};

export default Home;
