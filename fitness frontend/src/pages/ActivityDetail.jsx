import { useEffect, useState } from "react";
import { useParams } from "react-router";
import { getActivityDetail } from "../services/api";

const ActivityDetail = () => {
  const { id } = useParams();
  const [data, setData] = useState(null);

  useEffect(() => {
    getActivityDetail(id).then((res) => setData(res.data));
  }, []);

  if (!data) return <p>Loading...</p>;

  return (
    <div className="min-h-screen p-6 text-white bg-black">
      <h1 className="text-3xl mb-4">Activity Details</h1>

      <div className="bg-white/10 p-6 rounded-xl">
        <p>Type: {data.type}</p>
        <p>Duration: {data.duration}</p>
        <p>Calories: {data.caloriesBurned}</p>

        <h2 className="mt-4 text-xl">AI Recommendation</h2>
        <p>{data.recommendation}</p>
      </div>
    </div>
  );
};

export default ActivityDetail;