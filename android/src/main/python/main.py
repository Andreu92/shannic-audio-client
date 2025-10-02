import json
import innertube

def search(query: str, next_token: str = None, max_results: int = 20):
    client = innertube.InnerTube("ANDROID")
    search_data = {}
    results = []

    while len(results) < max_results:
        response = client.search(query=query, continuation=next_token)

        contents = (
            response.get("contents" if not next_token else "continuationContents", {})
                    .get("sectionListRenderer" if not next_token else "sectionListContinuation", {})
                    .get("contents", [])
        )

        for section in contents:
            items = section.get("itemSectionRenderer", {}).get("contents", [])
            for item in items:
                video = None
                if "elementRenderer" in item:
                    video = item["elementRenderer"].get("videoRenderer")
                if "compactVideoRenderer" in item:
                    video = item["compactVideoRenderer"]

                if video is None: continue

                video_id = video.get("videoId")
                title = "".join([r.get("text", "") for r in video.get("title", {}).get("runs", [])])
                author = "".join([r.get("text", "") for r in video.get("longBylineText", {}).get("runs", [])])
                thumbnails = video.get("thumbnail", {}).get("thumbnails", [])
                duration = video.get("lengthText", {}).get("runs")[0].get("text")

                results.append({
                    "id": video_id,
                    "title": title,
                    "author": author,
                    "thumbnails": thumbnails,
                    "duration": duration
                })

                if len(results) >= max_results:
                    break
            if len(results) >= max_results:
                break

        continuations = (
            response.get("contents" if not next_token else "continuationContents", {})
                    .get("sectionListRenderer" if not next_token else "sectionListContinuation", {})
                    .get("continuations", [])
        )

        if continuations:
            next_token = continuations[0].get("nextContinuationData", {}).get("continuation")
        else:
            break

    search_data["next_token"] = next_token
    search_data["results"] = results

    return json.dumps(search_data)

def get(video_id: str) -> str:
    client = innertube.InnerTube("ANDROID")
    player = client.player(video_id)

    details = player["videoDetails"]
    formats = player["streamingData"]["adaptiveFormats"]

    audio_streams = [
        {
            "bitrate": f["bitrate"],
            "duration": f["approxDurationMs"],
            "url": f["url"]
        }
        for f in formats
        if f["mimeType"].startswith("audio/")
    ]

    best_audio = max(audio_streams, key=lambda x: x["bitrate"])

    return json.dumps({
        "id": video_id,
        "title": details["title"],
        "author": details["author"],
        "duration": int(best_audio["duration"]),
        "thumbnails": details["thumbnail"]["thumbnails"],
        "url": best_audio["url"]
    })
