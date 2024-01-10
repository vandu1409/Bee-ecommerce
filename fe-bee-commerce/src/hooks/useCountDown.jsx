import {useEffect, useState} from "react";
import {differenceInDays, differenceInHours, differenceInMinutes, differenceInSeconds,} from "date-fns";

const useCountDown = (targetDate) => {
    const [days, setDays] = useState(0);
    const [hours, setHours] = useState(0);
    const [minutes, setMinutes] = useState(0);
    const [seconds, setSeconds] = useState(0);

    useEffect(() => {
        const interval = setInterval(() => {
            const now = new Date().getTime();
            const difference = targetDate - now;
            if (difference <= 0) {
                clearInterval(interval);
                return;
            }
            const day = differenceInDays(targetDate, now);
            const hour = differenceInHours(targetDate, now) % 24;
            const minute = differenceInMinutes(targetDate, now) % 60;
            const second = differenceInSeconds(targetDate, now) % 60;

            setDays(day > 0 ? day : 0);
            setHours(hour > 0 ? hour : 0);
            setMinutes(minute > 0 ? minute : 0);
            setSeconds(second > 0 ? second : 0);
        }, 1000);

        return () => clearInterval(interval);
    }, [targetDate]);

    return { days, hours, minutes, seconds };
};

export default useCountDown;
