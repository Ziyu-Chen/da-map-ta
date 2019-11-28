import axios from "axios";

const setColumns = columns => ({
  type: "SET_COLUMNS",
  columns
});

const setLocations = locations => ({
  type: "SET_LOCATIONS",
  locations
});

export const selectColumn = num => ({
  type: "SELECT_COLUMN",
  num
});

export const getColumns = file => {
  return async dispatch => {
    try {
      const data = new FormData();
      data.append("file", file, file.name);
      let columns = await axios.post("/columns", data, {
        headers: {
          "Content-Type": `multipart/form-data; boundary=${data._boundary}`
        }
      });
      columns = columns.data;
      return dispatch(setColumns(columns));
    } catch (e) {
      console.log(e);
    }
  };
};

export const getTable = (file, cols) => {
  return async dispatch => {
    try {
      const data = new FormData();
      data.append("file", file, file.name);
      let table = await axios.post("/table?cols=" + cols, data, {
        headers: {
          "Content-Type": `multipart/form-data; boundary=${data._boundary}`
        }
      });
      table = table.data;
      const columns = table[0];
      const locations = [];
      for (let i = 1; i < table.length; i++) {
        const temp = {};
        for (let j = 0; j < columns.length; j++) {
          temp[columns[j]] = table[i][j];
        }
        locations.push(temp);
      }
      console.log(locations);
      return dispatch(setLocations(locations));
    } catch (e) {
      console.log(e);
    }
  };
};
