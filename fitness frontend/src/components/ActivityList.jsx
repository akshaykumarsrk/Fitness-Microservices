import { useEffect, useState } from "react";
import { getActivities } from "../services/api";
import { motion } from "framer-motion";
import { useNavigate } from "react-router";

const ActivityList = () => {
  const [activities, setActivities] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    getActivities().then((res) => setActivities(res.data));
  }, []);

  return (
    <div>
      <h2 className="text-2xl font-semibold text-white mb-4">
        📊 Your Activities
      </h2>

      <div className="">
        <div className="grid md:grid-cols-3 gap-6">
        {activities.map((a) => (
          <motion.div
            key={a.id}
            whileHover={{ scale: 1.05 }}
            onClick={() => navigate(`/activities/${a.id}`)}
            className="cursor-pointer bg-gradient from-gray-800 to-gray-900 
                       border border-white/10 rounded-2xl p-5 shadow-lg 
                       hover:shadow-2xl transition-all duration-300 "
          >
            <h3 className="text-xl font-bold text-white mb-2">
              {a.type}
            </h3>

            <div className="space-y-1 text-gray-300">
              <p>⏱ {a.duration} min</p>
              <p>🔥 {a.caloriesBurned} kcal</p>
            </div>
          </motion.div>
        ))}
      </div>
      </div>
    </div>
  );
};

export default ActivityList;