import { YoutubeDownloader } from '@capacitor-community/youtube-dl';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    YoutubeDownloader.echo({ value: inputValue })
}
