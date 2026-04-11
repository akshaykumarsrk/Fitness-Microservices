import { useContext, useEffect } from "react";
import { AuthContext } from "react-oauth2-code-pkce";
import { useDispatch } from "react-redux";
import { BrowserRouter, Routes, Route, Navigate } from "react-router";

import { setCredentials } from "./store/authSlice";

import Landing from "./pages/Landing";
import Dashboard from "./pages/Dashboard";
import ActivityDetail from "./pages/ActivityDetail";

function App() {
  const { token, tokenData, logIn, logOut } = useContext(AuthContext);
  const dispatch = useDispatch();

  useEffect(() => {
    if (token) {
      dispatch(setCredentials({ token, user: tokenData }));
    }
  }, [token]);

  return (
    <BrowserRouter>
      {!token ? (
        <Routes>
          <Route path="/" element={<Landing logIn={logIn} />} />
          <Route path="*" element={<Navigate to="/" />} />
        </Routes>
      ) : (
        <Routes>
          <Route path="/activities" element={<Dashboard logOut={logOut} />} />
          <Route path="/activities/:id" element={<ActivityDetail />} />
          <Route path="*" element={<Navigate to="/activities" />} />
        </Routes>
      )}
    </BrowserRouter>
  );
}

export default App;