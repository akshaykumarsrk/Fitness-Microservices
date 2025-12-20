import { Box, Button, colors, FormControl, InputLabel, MenuItem, Select, TextField } from '@mui/material'
import React, { useState } from 'react'
import { addActivity } from '../services/api';

const ActivityForm = ({onActivityAdded}) => {

  // this state tracks which activity is selected
  const [activity, setActivity] = useState({
    type: "RUNNING",
    duration: '',
    caloriesBurned: '',
    additionalMetrics: {}
  });

  // function to handle submission which is an asynchronous function
  const handleSubmit = async (e) => {
    e.preventDefault(); // to disable default behaviour of form

    try {
      // this function will call API and send our frontend data to backend
      await addActivity(activity)
      onActivityAdded();
      setActivity({
        type: "RUNNING",
        duration: '',
        caloriesBurned: ''
      });
    } catch (error) {
      console.error(error);
    }
  }

  return (
    <Box component="form" onSubmit={handleSubmit} sx={
      { mb: 4, input: { color: "white" },
            label: { color: "white" },
            "& .MuiOutlinedInput-notchedOutline": {
              borderColor: "white",
            },
            "&:hover .MuiOutlinedInput-notchedOutline": {
              borderColor: "white",
    }, }} >
      {/* FormControl is using because we are grouping things here i.e showing select box (drop down menu) */}
      <FormControl fullWidth sx={{mb: 2,
    "& .MuiInputLabel-root": {
      color: "white",
    },
    "& .MuiOutlinedInput-root": {
      color: "white",
      "& fieldset": {
        borderColor: "white",
      },
      "&:hover fieldset": {
        borderColor: "white",
      },
      "&.Mui-focused fieldset": {
        borderColor: "white",
      },
    },
    "& .MuiSvgIcon-root": {
      color: "white", // dropdown arrow
    },}}>
        <InputLabel>Activity Type</InputLabel>
        <Select
          value={activity.type} // only the type will change, rest things remain as it is
          onChange={(e) => {setActivity({...activity, type: e.target.value})}} // destructure the activity object
        >
          <MenuItem value="RUNNING">Running</MenuItem>
          <MenuItem value="WALKING">Walking</MenuItem>
          <MenuItem value="CYCLING">Cycling</MenuItem>
        </Select>
      </FormControl>

      <TextField fullWidth
      label="Duration (Minutes)"
      type='number'
      sx={{mb: 2, input: { color: "white" },
                  label: { color: "white" },
                  "& .MuiOutlinedInput-notchedOutline": {
                    borderColor: "white",
                  },
                  "&:hover .MuiOutlinedInput-notchedOutline": {
                    borderColor: "white",
    },}}
      value={activity.duration}
      onChange={(e) => {setActivity({...activity, duration: e.target.value})}}
      />

      <TextField fullWidth
      label="Calories Burned"
      type='number'
      sx={{mb: 2, input: { color: "white" },
    label: { color: "white" },
    "& .MuiOutlinedInput-notchedOutline": {
      borderColor: "white",
    },
    "&:hover .MuiOutlinedInput-notchedOutline": {
      borderColor: "white",
    },}}
      value={activity.caloriesBurned}
      onChange={(e) => {setActivity({...activity, caloriesBurned: e.target.value})}}
      />

      <Button type='submit' variant='contained'>
        Add Activity
      </Button>
    </Box>
  )
}

export default ActivityForm