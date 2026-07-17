import AmountInput from "./amountInput";
import Dropdown from "./dropdown"

const difficulties = [
    { label: "Any", value: "" },
    { label: "Easy", value: "Easy" },
    { label: "Medium", value: "Medium" },
    { label: "Hard", value: "Hard" }
]

const types = [
    { label: "Any", value: "" },
    { label: "Multiple Choice", value: "MultipleChoice" },
    { label: "True / False", value: "TrueFalse" }
]

const categories = [
    { label: "Any", value: "" },
    { label: "General Knowledge", value: "GeneralKnowledge" },
    { label: "Entertainment Books", value: "EntertainmentBooks" },
    { label: "Entertainment Film", value: "EntertainmentFilm" },
    { label: "Entertainment Music", value: "EntertainmentMusic" },
    { label: "Entertainment Musicals & Theatres", value: "EntertainmentMusicalsAndTheatres" },
    { label: "Entertainment Television", value: "EntertainmentTelevision" },
    { label: "Entertainment Video Games", value: "EntertainmentVideoGames" },
    { label: "Entertainment Board Games", value: "EntertainmentBoardGames" },
    { label: "Science & Nature", value: "ScienceAndNature" },
    { label: "Science Computers", value: "ScienceComputers" },
    { label: "Science Mathematics", value: "ScienceMathematics" },
    { label: "Mythology", value: "Mythology" },
    { label: "Sports", value: "Sports" },
    { label: "Geography", value: "Geography" },
    { label: "History", value: "History" },
    { label: "Politics", value: "Politics" },
    { label: "Art", value: "Art" },
    { label: "Celebrities", value: "Celebrities" },
    { label: "Animals", value: "Animals" },
    { label: "Vehicles", value: "Vehicles" },
    { label: "Entertainmen tComics", value: "EntertainmentComics" },
    { label: "Science Gadgets", value: "ScienceGadgets" },
    { label: "Entertainment Japanese Anime & Manga", value: "EntertainmentJapaneseAnimeAndManga" },
    { label: "Entertainment Cartoon & Animations", value: "EntertainmentCartoonAndAnimations" },
]

export default function QuizSettings({
    dropdownValueChanged
}: {
    dropdownValueChanged: (label: string, value: string) => void;
}) {
    return (
        <>
            <div className="flex gap-6 p-4 field-sizing-content">  
                <AmountInput 
                    quizSettingsChanged={dropdownValueChanged}
                />
            </div>
            <div className="flex gap-6 p-4">
                <Dropdown 
                    label="Difficulty" 
                    items={difficulties}
                    optionClicked={dropdownValueChanged}                
                />
                <Dropdown 
                    label="Type" 
                    items={types}
                    optionClicked={dropdownValueChanged}   
                />
                <Dropdown 
                    label="Category" 
                    items={categories}
                    optionClicked={dropdownValueChanged}   
                />
            </div>
        </>
    )
}