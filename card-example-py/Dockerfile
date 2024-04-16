FROM bde2020/hadoop-base:2.0.0-hadoop3.2.1-java8

# Dumb using miniconda just for python but this worked and apt-get didn't
# Download Miniconda installer
RUN curl -O https://repo.anaconda.com/miniconda/Miniconda3-latest-Linux-x86_64.sh
# Run the installer with options to avoid interactive prompts
RUN bash Miniconda3-latest-Linux-x86_64.sh -b -p /opt/miniconda
# Optionally, create a new conda environment
RUN /opt/miniconda/bin/conda create -y -n myenv python=3.10
# Activate the conda environment
RUN /opt/miniconda/bin/conda init bash && echo "conda activate myenv" >> ~/.bashrc

WORKDIR ~/code
COPY . .
ENV PATH="/opt/miniconda/bin:${PATH}"
RUN pip install -r requirements.txt

ENTRYPOINT [ "python", "cards.py", "decks.jsonl" ]
