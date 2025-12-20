// import './App.css'
import { Box, Button, Typography } from "@mui/material"
import { useContext, useEffect, useState } from "react"
import { AuthContext } from "react-oauth2-code-pkce"
import { useDispatch } from "react-redux"
import { BrowserRouter as Router, Navigate, Route, Routes, useLocation } from "react-router"
import { setCredentials } from "./store/authSlice"
import ActivityList from "./components/ActivityList"
import ActivityForm from "./components/ActivityForm"
import ActivityDetail from "./components/ActivityDetail"
import image from './assets/bg2.jpg'
import image2 from './assets/bg4.jpg'

// To combine ActivityForm and ActivityList on single page
// in ActivityPage, these two components ActivityForm and ActivityList are render
const ActivitiesPage = () => {
  return (
    <Box sx={{ p: 2, border: '1px dashed grey' }}>
      <ActivityForm onActivityAdded = {() => window.location.reload()} />  {/* in this component onActivitiesAdded prop is added which is a function used to reload the page  */}
      <ActivityList />
    </Box>
  );
}

function App() {
  
  const {token, tokenData, logIn, logOut, isAuthenticated } = useContext(AuthContext);
  const dispatch = useDispatch();
  const [authReady, setAuthReady] = useState(false);

  // useEffect takes two things - arrow function and  array of dependencies
  // if dependency change then function execute
  useEffect(() => {
    if(token) // if token comes so set credentials and in setCredentials you are setting token, user playload and userId information in local storage
    {         
      dispatch(setCredentials({token, user: tokenData})); // token and user data comes in payload
      setAuthReady(true);
    }
  }, [token, tokenData, dispatch]);

  return (

    <Router>

      {/* now what will do */}
      {/* we can authenticate the user from this login page i.e atleast showing token information (not making too complex) */}
      {/* token information means decoded token information */}


      {/* if token is not present then show login button to create token */}
      {!token ? (
        
        <Box
      sx={{
        height: "100vh",
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
        textAlign: "center",
        color: "white",

         /* Background image styles */
    backgroundImage: `url(${image})`,
    backgroundSize: "cover",
    backgroundPosition: "center",
    backgroundRepeat: "no-repeat",

    /* Optional overlay effect */
    position: "absolute",
      top: 0,
      left: 0,
      width: "100%",
      height: "100%",
      backgroundColor: "rgba(0, 0, 0, 0.5)", // ⬅ reduce opacity here
      zIndex: 1,
      }}
    >
      <Typography variant="h4" gutterBottom>
        Welcome to the Fitness Tracker App
      </Typography>
      <Typography variant="subtitle1" sx={{ mb: 3 }}>
        Please login to access your activities
      </Typography>
      <Button variant="contained" color="primary" size="large" onClick={() => {
                logIn();
              }}>
        LOGIN
      </Button>
    </Box>
      ) : (

              // <div>
              //   <pre>{JSON.stringify(tokenData, null, 2)}</pre>
              //   <pre>{JSON.stringify(token, null, 2)}</pre>
              // </div>

          <Box sx={{ p: 2, border: '1px dashed grey',
              minHeight: "100vh",
              height: "auto",
              backgroundImage: `url(${image2})`,
              backgroundSize: "cover",
              backgroundPosition: "center",
              backgroundRepeat: "no-repeat",
              color: "white",

              position: "relative",
              inset: 0,
              backgroundColor: "rgba(0, 0, 0, 0.35)", // control opacity here
              zIndex: 1,
              }}>
            <Button variant="contained" color="secondary" onClick={logOut}>
              LOGOUT
            </Button>
            
            {/* we will requied two routes here */}
            {/* one for combined page of ActivityForm and ActivityList */}
            {/* second for ActivityDetail */}
            <Routes>
              {/* To render these Activities on pages*/}
              <Route path="/activities" element={<ActivitiesPage />} />
              <Route path="/activities/:id" element={<ActivityDetail />} /> {/* here we are passing id in ActivityDetail which tell us which activity we will render */}
              <Route path="/" element={token ? <Navigate to="/activities" replace /> : 
                                    <div>Welcome! Please login</div>} /> {/* if token available so naviagte to /activities url */}
            </Routes>
          </Box>
      )}
    </Router>
  )
}

export default App
