import axios from "axios"

const API_URL = 'http://localhost:8080/api/v1'   // base URL

// in javaScript lots of inBuilt things are there like fetch API is also there which is used to call APIs
// but we are using axios which is also a library through which we can call APIs
// it brings many features like intercepters and all which we will use
// create axios instance
const api = axios.create({
    baseURL: API_URL
});

// interceptors is good feature of axios where we provides interceptors through which we can intercept every request and do modifications
// here we are intercepting (catching) the request
api.interceptors.request.use((config) => {
    const userId = localStorage.getItem('userId');
    const token = localStorage.getItem('token');
    if(token)
    {
        config.headers['Authorization'] = `Bearer ${token}`;
    }

    if(userId)
    {
        config.headers['X-User-ID'] = userId;
    }

    return config; // with the help of config object we have setted header and return it, now axios will manage it in terms of modified header
})

// to get all the APIs of activities
export const getActivities = () => api.get('/activities/getActivity');

// to post the API and we are sending object which contains activities data
export const addActivity = (activity) => api.post('/activities/track', activity);

// to get recommendations by activity
export const getActivityDetail = (id) => api.get(`/recommendations/activity/${id}`);


// in every API request, one thing is to be sent i.e Authorization Header
// in PostMan we were not sending Authentication Header in every request because we were authenticate the request from Authorization Server
// by using Authorization Tab and select OAuth2 so token authomatically embeded
// so PostMan is a client that take care of all these things but in code we have to take care of all the things
// so what we will do, we will set a Authorization header in every request 
// and also set userId in every request as a header because in some endpoints userId can also be needed
// i am adding userId by default in header in every request so whenever it needs we can use in backend else skip it