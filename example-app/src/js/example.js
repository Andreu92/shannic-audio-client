import { AudioClient } from '@shannic/audio-client';

window.testEcho = () => {
    AudioClient.search({ query: 'avenged sevenfold' }).then((results) => {
        console.log(results);
    });
    AudioClient.get({ videoId: '94bGzWyHbu0' }).then((results) => {
        console.log(results);
    });
}
