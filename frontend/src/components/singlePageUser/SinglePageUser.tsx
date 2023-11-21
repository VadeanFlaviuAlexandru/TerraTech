import {
  Legend,
  Line,
  LineChart,
  ResponsiveContainer,
  Tooltip,
  XAxis,
  YAxis,
} from "recharts";
import "./singlePageUser.scss";

type Props = {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  role: string;
  createdAt: string;
  username: string;
  enabled: boolean;
  credentialsNonExpired: boolean;
  accountNonExpired: boolean;
  accountNonLocked: boolean;
  // chart?: {
  //   dataKeys: { name: string; color: string }[];
  //   data: object[];
  // };
  // activities?: { time: string; text: string }[];
};

const Single = (props: Props) => {
  return (
    <div className="single">
      <div className="view">
        <div className="info">
          <div className="topInfo">
            {/* {props.img && <img src={props.img} alt="" />} */}
            <h1>
              {props.firstName} {props.lastName}
            </h1>
            <button>Update</button>
          </div>
          <div className="details">
            {Object.entries(props).map(([key, value]) => (
              <div className="item" key={key}>
                <span className="itemTitle">{key}</span>
                <span className="itemValue">{value}</span>
              </div>
            ))}
          </div>
        </div>
        <hr />
        {/* {props.chart && (
          <div className="chart">
            <ResponsiveContainer width="100%" height="100%">
              <LineChart
                width={500}
                height={300}
                data={props.chart.data}
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
                {props.chart.dataKeys.map((dataKey) => (
                  <Line
                    type="monotone"
                    dataKey={dataKey.name}
                    stroke={dataKey.color}
                  />
                ))}
              </LineChart>
            </ResponsiveContainer>
          </div>
        )} */}
      </div>
      <div className="activities">
        <h2>Latest Activities</h2>
        {/* {props.activities && (
          <ul>
            {props.activities.map((activity) => (
              <li key={activity.text}>
                <div>
                  <p>{activity.text}</p>
                  <time>{activity.time}</time>
                </div>
              </li>
            ))}
          </ul>
        )} */}
      </div>
    </div>
  );
};

export default Single;
