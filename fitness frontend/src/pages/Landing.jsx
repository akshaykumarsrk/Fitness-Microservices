import bg from "../assets/bg2.jpg";

const Landing = ({ logIn }) => {
  return (
    <div
      style={{ backgroundImage: `url(${bg})` }}
      className="h-screen bg-cover bg-center flex items-center justify-center"
    >
      <div className="bg-black/50 backdrop-blur-xl p-10 rounded-2xl text-center text-white">
        
        <h1 className="text-4xl font-bold mb-4">
          Fitness AI 🚀
        </h1>

        <p className="mb-6">
          Track your workouts with AI insights
        </p>

        <button
          onClick={() => logIn()}
          className="bg-blue-500 px-6 py-2 rounded-lg hover:bg-blue-600 cursor-pointer"
        >
          Login
        </button>

        <button
          className="ml-4 bg-green-500 px-6 py-2 rounded-lg  hover:bg-green-600 cursor-pointer"
          onClick={() => logIn()}
        >
          Register
        </button>
      </div>
    </div>
  );
};

export default Landing;