import axios from "axios";
import ApiConstants from "../constants/ApiConstant";

const joinRoom = () => axios.get(
    ApiConstants.JOIN_ROOM_URL,
    {
        withCredentials: true
    }
);

const leaveRoom = (roomId) => axios.get(
    ApiConstants.LEAVE_ROOM_URL(roomId),
    {
        withCredentials: true
    }
)

const gameService = {
    joinRoom,
    leaveRoom
}

export default gameService;