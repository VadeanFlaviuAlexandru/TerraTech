import { useEffect, useState } from "react";
import "react-toastify/dist/ReactToastify.css";
import {
  fetchAllManagers,
  fetchStatistics,
} from "../../api/statistics/StatisticsApi";
import ChartBox from "../../components/charts/chartBox/ChartBox";
import PieChartBox from "../../components/charts/pieChartBox/PieChartBox";
import TopBox from "../../components/charts/topBox/TopBox";
import Loading from "../../components/icons/Loading";
import {
  allManagersSetter,
  resetAllManagers,
} from "../../store/AllManagers/AllManagersSlice";
import {
  resetStatistics,
  statisticsSetter,
} from "../../store/Statistics/StatisticsSlice";
import { useAppDispatch, useAppSelector } from "../../store/hooks";
import { warningToast } from "../../utils/toasts/userToasts";
import "./home.scss";

const Home = () => {
  const statistics = useAppSelector((state) => state.statistics);
  const currentUser = useAppSelector((state) => state.currentUser);
  const allManagers = useAppSelector((state) => state.allManagers.managers);
  const dispatch = useAppDispatch();
  const [loading, setLoading] = useState(true);
  const [selected, setSelected] = useState(-1);
  const [fetch, setFetch] = useState(false);

  useEffect(() => {
    if (selected !== -1) {
      const fetchData = async () => {
        try {
          await fetchStatistics(selected, currentUser.token).then(
            (response) => {
              dispatch(statisticsSetter(response));
              setLoading(false);
            }
          );
        } catch (err: any) {
          warningToast(err.stringify);
        }
      };
      fetchData();
      setFetch(false);
    }
  }, [fetch]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        if (currentUser.user.role === "ROLE_ADMIN") {
          if (allManagers?.length === 0) {
            await fetchAllManagers(currentUser.token).then((response) => {
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
          await fetchStatistics(currentUser.user.id, currentUser.token).then(
            (response) => {
              dispatch(statisticsSetter(response));
              setLoading(false);
            }
          );
        }
      } catch (err: any) {
        warningToast(err.stringify);
      }
    };
    fetchData();
    return () => {
      dispatch(resetStatistics());
      setLoading(true);
    };
  }, [selected]);

  useEffect(() => {
    return () => {
      dispatch(resetAllManagers());
    };
  }, []);

  return (
    <>
      {allManagers.length > 0 && (
        <select
          value={1}
          onChange={(e) => setSelected(parseInt(e.target.value))}
        >
          <option>Select a manager</option>
          {allManagers.map(({ id, firstName }) => (
            <option key={id} value={id}>
              {firstName}
            </option>
          ))}
        </select>
      )}
      {loading ? (
        allManagers?.length === 0 && currentUser.user.role === "ROLE_ADMIN" ? (
          <div>There are no managers to show the statistics</div>
        ) : (
          <div className="gears">
            <div className="animations">
              <span>Loading statistics...</span>
              <Loading />
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
