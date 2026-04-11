import { useNavigate } from "react-router";

const ActivityCard = ({ activity }) => {
  const navigate = useNavigate();

  return (
    <div
      onClick={() => navigate(`/activities/${activity.id}`)}
      className="cursor-pointer p-5 rounded-2xl 
           bg-gradient from-gray-800/80 to-gray-900/80
           border border-white/10
           shadow-lg hover:shadow-purple-500/20
           transition-all duration-300"
    >
      <h2>{activity.type}</h2>
      <p>{activity.duration} min</p>
      <p>{activity.caloriesBurned} kcal</p>
    </div>
  );
};

export default ActivityCard;